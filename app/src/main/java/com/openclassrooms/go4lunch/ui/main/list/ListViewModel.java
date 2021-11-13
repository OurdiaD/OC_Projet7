package com.openclassrooms.go4lunch.ui.main.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;
import com.openclassrooms.go4lunch.models.User;
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

    public MutableLiveData<List<User>> getUserByPlaceId(String placeId) {
        return userRepository.getUserByPlaceId(placeId);
    }
}