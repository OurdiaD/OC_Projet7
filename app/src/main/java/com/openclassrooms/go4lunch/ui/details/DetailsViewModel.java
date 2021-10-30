package com.openclassrooms.go4lunch.ui.details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public DetailsViewModel() {
        placeRepository = PlaceRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<ResultDetails> getDetailsOfPlace(String placeId) {
        Log.d("lol repo details", "" + placeRepository.getDetails(placeId));
        return placeRepository.getDetails(placeId);
    }

    public void addSelectPlace(String placeId, String name, String vicinity) {
        userRepository.addSelectPlace(placeId, name,  vicinity);
    }

    public MutableLiveData<List<User>> getUserByPlaceId(String placeId) {
        return userRepository.getUserByPlaceId(placeId);
    }
}
