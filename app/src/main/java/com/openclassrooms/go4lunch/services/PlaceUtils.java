package com.openclassrooms.go4lunch.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.Photo;
import com.openclassrooms.go4lunch.models.maps.Result;

import java.util.ArrayList;
import java.util.List;

public class PlaceUtils {

    public static String getPhotoUrl(List<Photo> photos){
        if (photos != null){
            String reference = photos.get(0).getPhoto_reference();
            String apiKey = BuildConfig.API_KEY;
            return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;
        }
        return "";
    }

    public static void getResultWithUser(Task<QuerySnapshot> taskUser, Result result) {
        List<User> usersList = new ArrayList<>();
        //listUser.addOnCompleteListener(taskUser -> {
            for (QueryDocumentSnapshot document : taskUser.getResult()) {
                User user = document.toObject(User.class);
                if (result.getPlace_id().equals(user.getPlaceId())) {
                    usersList.add(user);
                }
                result.setListUser(usersList);
            }
       // });
    }
}
