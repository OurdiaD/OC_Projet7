package com.openclassrooms.go4lunch.datas.repositories;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.datas.MapsInterface;
import com.openclassrooms.go4lunch.datas.RetrofitClient;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;
import com.openclassrooms.go4lunch.models.maps.RootDetails;
import com.openclassrooms.go4lunch.models.maps.RootList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlaceRepository {

    private static LatLng currentLatLng;
    MutableLiveData<List<Result>> listOfPlace;
    MapsInterface mapsInterface;
    String apiKey;

    static Location currentLocation;
    public static PlaceRepository placeRepository;

    public static PlaceRepository getInstance(){
        if (placeRepository == null) {
            placeRepository = new PlaceRepository();
            Log.d("lol repo38", "intance null");
        }
        Log.d("lol repo40", "intance");
        return placeRepository;
    }

    public PlaceRepository(){
        Retrofit retro = RetrofitClient.getClient("https://maps.googleapis.com/maps/");
        mapsInterface = RetrofitClient.getInterface();
        apiKey = BuildConfig.API_KEY;
    }

    public static void setCurrentLocation(Location location){
        currentLocation = location;
        currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        Log.d("lol repo",""+location);
    }

    public LatLng getLatLng(){
        return currentLatLng;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public MutableLiveData<List<Result>> getListOfPlace(){
        if (listOfPlace == null){
            Log.d("lol repo68", "if null reponse");
            requestListOfPlace();
        }
        Log.d("lol repo71", "reponse");
        return listOfPlace;
    }

    public void requestListOfPlace()  {
        listOfPlace = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("location", currentLatLng.latitude +","+currentLatLng.longitude);
        params.put("radius", "1000");
        params.put("type", "restaurant");
        //params.put("rankby", "distance");
        params.put("key", apiKey);
        Call<RootList> placesResult = mapsInterface.getAllPlaces(params);

        Log.d("lol repo85", ""+ placesResult.isExecuted());
        Log.d("lol repo86", ""+ placesResult.toString());
        placesResult.enqueue(new Callback<RootList>() {
            @Override
            public void onResponse(@NonNull Call<RootList> call, @NonNull Response<RootList> response) {
                Log.d("lol response", "onrep" );
                if (response.body() != null){
                    //listOfPlace.setValue(response.body());
                    listOfPlace.setValue(response.body().getResults());
                    Log.d("lol response", "" + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootList> call, @NonNull Throwable t) {
                //listOfPlace.postValue(null);
                listOfPlace = null;
                Log.d("lol reqplace fail", ""+t );
            }
        });

        //return listOfPlace;

    }

    public MutableLiveData<ResultDetails> getDetails(String placeId) {
        MutableLiveData<ResultDetails> detailsPlace = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("place_id", placeId);
        params.put("key", apiKey);
        Call<RootDetails> placeDetails = mapsInterface.getDetailsPlace(params);
        placeDetails.enqueue(new Callback<RootDetails>() {
            @Override
            public void onResponse(@NonNull Call<RootDetails> call, @NonNull Response<RootDetails> response) {
                Log.d("lol details ok", ""+response.body() );
                Log.d("lol details ok", ""+response.body().getResult() );
                detailsPlace.setValue(response.body().getResult());
            }

            @Override
            public void onFailure(@NonNull Call<RootDetails> call, @NonNull Throwable t) {
                Log.d("lol details fail", ""+t );
            }
        });
        return detailsPlace;
    }
}
