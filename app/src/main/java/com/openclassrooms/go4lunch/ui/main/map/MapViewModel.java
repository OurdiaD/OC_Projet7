package com.openclassrooms.go4lunch.ui.main.map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.maps.Result;

import java.util.List;

public class MapViewModel extends ViewModel {

    private final PlaceRepository repository;

    public MapViewModel() {
        repository = PlaceRepository.getInstance();
    }

    public MutableLiveData<List<Result>> getListOfPlace() {
        return repository.getListOfPlace();
    }

    public LatLng getLatLng(){
        return repository.getLatLng();
    }

}