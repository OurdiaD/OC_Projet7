package com.openclassrooms.go4lunch.services;

import static android.app.Notification.DEFAULT_ALL;
import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.ui.main.MainActivity;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Notification extends Worker {

    public Notification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = this.getApplicationContext();
        // Intent to start when notification is tapped
        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_dining)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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
            channel.setDescription("Your lunch");
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
        userRepository.getUserData().addOnCompleteListener(task -> {
            User user = task.getResult().toObject(User.class);
            String namePlace = Objects.requireNonNull(user).getPlaceName();
            String addressPlace = user.getPlaceAddress();
            builder.setContentTitle(namePlace);
            stringBuilder.append(addressPlace).append("\n");

            userRepository.getUserByPlaceIdQuery(user.getPlaceId()).get().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task1.getResult()){
                        User user1 = document.toObject(User.class);
                        stringBuilder.append(user1.getFullname()).append("\n");
                    }
                    builder.setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(stringBuilder));
                }
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(1, builder.build());
            });
        });
    }

    public static void setupNotif(Context context) {
        String PREFS = "PREFS_GO4LUNCH";
        String PREFS_NOTIF = "PREFS_NOTIF";
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, MODE_PRIVATE);
        boolean statNotif = sharedPreferences.getBoolean(PREFS_NOTIF, true);

        if (!statNotif) return;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long current = System.currentTimeMillis()/1000;
        long date = calendar.getTimeInMillis()/1000;
        if (current > date){
            date = date + 86400;
        }
        long l = date - current ;

        //WorkManager.getInstance(getApplicationContext()).cancelAllWork();

        PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(Notification.class, 24, TimeUnit.HOURS)
                .setInitialDelay(l, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("GO4LUNCH", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
    }
}
