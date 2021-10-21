package com.openclassrooms.go4lunch.ui.main.workmates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.openclassrooms.go4lunch.datas.repositories.UserRepository;

public class WorkmatesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private UserRepository repository;

    public WorkmatesViewModel() {
        /*mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");*/
        repository = new UserRepository();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public CollectionReference getUsersCollection(){
        return repository.getUsersCollection();
    }

}