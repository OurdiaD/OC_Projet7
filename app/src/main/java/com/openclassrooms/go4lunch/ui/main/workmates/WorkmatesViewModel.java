package com.openclassrooms.go4lunch.ui.main.workmates;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;

public class WorkmatesViewModel extends ViewModel {

    private final UserRepository repository;

    public WorkmatesViewModel() {
        repository = new UserRepository();
    }

    public CollectionReference getUsersCollection(){
        return repository.getUsersCollection();
    }

}