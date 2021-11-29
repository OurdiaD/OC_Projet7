package com.openclassrooms.go4lunch.ui.main.list;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.maps.Result;

import java.util.List;

public class ListViewModel extends ViewModel {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public ListViewModel() {
        placeRepository = PlaceRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<List<Result>> getListOfPlace() {
        return placeRepository.getListOfPlace();
    }

    public Location getLocation(){
        return PlaceRepository.getCurrentLocation();
    }

    public Task<QuerySnapshot> getUserCollection(){
        return userRepository.getUsersCollection().get();
    }
}