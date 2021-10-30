package com.openclassrooms.go4lunch.datas.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private static volatile UserRepository instance;
    private static final String COLLECTION_NAME = "users";

    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserRepository.class){
            if (instance == null){
                instance = new UserRepository();
            }
            return instance;
        }
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getCurrentUserUID(){
        FirebaseUser user = getCurrentUser();
        return (user != null)? user.getUid() : null;
    }

    // Get the Collection Reference
    public CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create User in Firestore
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if(user != null){
            String email= user.getEmail();
            String username = user.getDisplayName();
            String uid = user.getUid();
            String photoUrl = String.valueOf(user.getPhotoUrl());
            Log.d("lol", photoUrl);

            User userToCreate = new User(uid, username, email, photoUrl);

            Task<DocumentSnapshot> userData = getUserData();
            // If the user already exist in Firestore, we get his data (isMentor)
            userData.addOnSuccessListener(documentSnapshot -> {

                this.getUsersCollection().document(uid).set(userToCreate);
            });

        }
    }

    // Get User Data from Firestore
    public Task<DocumentSnapshot> getUserData(){
        String uid = this.getCurrentUserUID();
        if(uid != null){
            return this.getUsersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    public Query getAllUsers(){
        return this.getUsersCollection()
                .document()
                .collection(COLLECTION_NAME)
                .orderBy("fullname");
    }

    public MutableLiveData<List<User>> getUserByPlaceId(String placeId) {
        MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();;
        List<User> users = new ArrayList<>();
        Query query = this.getUsersCollection().whereEqualTo("placeId", placeId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()){
                        users.add(document.toObject(User.class));
                    }
                    usersLiveData.postValue(users);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                usersLiveData.postValue(null);
            }
        });
        return usersLiveData;
    }

    public void addSelectPlace(String placeId, String name, String vicinity) {
        FirebaseUser user = getCurrentUser();
        String uid = user.getUid();
        Task<DocumentSnapshot> userData = getUserData();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        Map<String, Object> data = new HashMap<>();
        data.put("placeId", placeId);
        data.put("placeAddress", vicinity);
        data.put("placeName", name);
        data.put("timestamp", ts);

        userData.addOnSuccessListener(documentSnapshot -> {
            this.getUsersCollection().document(uid).update(data);
        });

    }
}
