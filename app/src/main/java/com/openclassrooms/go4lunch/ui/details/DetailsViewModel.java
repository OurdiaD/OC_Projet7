package com.openclassrooms.go4lunch.ui.details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    private PlaceRepository repository;

    public DetailsViewModel() {
        repository = PlaceRepository.getInstance();
    }

    public MutableLiveData<ResultDetails> getDetailsOfPlace(String placeId) {
        Log.d("lol repo details", "" + repository.getDetails(placeId));
        return repository.getDetails(placeId);
    }
}
