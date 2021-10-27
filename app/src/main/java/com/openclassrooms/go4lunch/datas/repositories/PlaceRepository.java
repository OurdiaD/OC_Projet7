package com.openclassrooms.go4lunch.datas.repositories;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.datas.MapsInterface;
import com.openclassrooms.go4lunch.datas.RetrofitClient;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.Root;

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
    }

    public static void setCurrentLocation(Location location){
        currentLocation = location;
        currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        /*LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);*/
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
            requestPlace();
        }
        Log.d("lol repo71", "reponse");
        return listOfPlace;
    }

    public void requestPlace()  {
        listOfPlace = new MutableLiveData<>();
        String apiKey = BuildConfig.API_KEY;
        Map<String, String> params = new HashMap<String, String>();
        params.put("location", currentLatLng.latitude +","+currentLatLng.longitude);
        params.put("radius", "1000");
        params.put("type", "restaurant");
        //params.put("rankby", "distance");
        params.put("key", apiKey);
        Call<Root> placesResult = mapsInterface.getAllPlaces(params);

        Log.d("lol repo85", ""+ placesResult.isExecuted());
        Log.d("lol repo86", ""+ placesResult.toString());
        placesResult.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Log.d("lol response", "onrep" );
                if (response.body() != null){
                    //listOfPlace.setValue(response.body());
                    listOfPlace.setValue(response.body().getResults());
                    Log.d("lol response", "" + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                //listOfPlace.postValue(null);
                listOfPlace = null;
                Log.d("lol response", ""+"failure" );
                Log.d("lol response", ""+call);
                Log.d("lol response", ""+t );
            }
        });

        //return listOfPlace;

    }
}
