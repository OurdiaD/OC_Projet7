package com.openclassrooms.go4lunch.services;

import static android.app.Notification.DEFAULT_ALL;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Observer;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.ui.main.MainActivity;

import java.util.List;


public class Notification extends Worker {

    public Notification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.d("lol notifcation","constru");
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = this.getApplicationContext();
        Log.d("lol notifcation","pass");
        // Intent to start when notification is tapped
        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel")
                //.setContentTitle("hello, world")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_dining)
                //.setContentText("textContent")
                // Only on api < 26, see createNotificationChannel otherwise
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Default sound, vibration etc
                // Only on api < 26, see createNotificationChannel otherwise
                .setDefaults(DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        getData(builder);

        /*NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());*/


        return Result.success();
    }

    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_channel", "MyApp notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("They will wake you up in the night");
            channel.enableVibration(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void getData(NotificationCompat.Builder builder) {
        StringBuilder stringBuilder = new StringBuilder();
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getUserData().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                String namePlace = user.getPlaceName();
                String addressPlace = user.getPlaceAddress();
                builder.setContentTitle(namePlace);
                stringBuilder.append(addressPlace).append("\n");

                userRepository.getUserByPlaceIdQuery(user.getPlaceId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                User user1 = document.toObject(User.class);
                                stringBuilder.append(user1.getFullname()).append("\n");
                            }
                            builder.setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(stringBuilder));
                        }
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        notificationManager.notify(1, builder.build());
                    }
                });
            }
        });
    }
}
