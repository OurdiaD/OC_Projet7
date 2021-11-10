package com.openclassrooms.go4lunch.datas.repositories;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.datas.MapsInterface;
import com.openclassrooms.go4lunch.datas.RetrofitClient;
import com.openclassrooms.go4lunch.models.maps.Prediction;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.ResultDetails;
import com.openclassrooms.go4lunch.models.maps.RootAutocomplete;
import com.openclassrooms.go4lunch.models.maps.RootDetails;
import com.openclassrooms.go4lunch.models.maps.RootList;

import java.util.ArrayList;
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
        }
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
    }

    public LatLng getLatLng(){
        return currentLatLng;
    }

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public MutableLiveData<List<Result>> getListOfPlace(){
        if (listOfPlace == null){
            requestListOfPlace();
        }
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

        placesResult.enqueue(new Callback<RootList>() {
            @Override
            public void onResponse(@NonNull Call<RootList> call, @NonNull Response<RootList> response) {
                if (response.body() != null){
                    listOfPlace.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootList> call, @NonNull Throwable t) {
                listOfPlace = null;
            }
        });
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
                detailsPlace.setValue(response.body().getResult());
            }

            @Override
            public void onFailure(@NonNull Call<RootDetails> call, @NonNull Throwable t) {
                Log.d("lol details fail", ""+t );
            }
        });
        return detailsPlace;
    }

    public void searchPlace(String search) {
        //listOfPlace = new MutableLiveData<>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("input", search);
        params.put("location", currentLatLng.latitude +","+currentLatLng.longitude);
        params.put("radius", "1000");
        params.put("type", "establishment");
        params.put("key", apiKey);
        Log.d("lol plrepo", " searchplace");
        Call<RootAutocomplete> placesResult = mapsInterface.searchPlaces(params);
        placesResult.enqueue(new Callback<RootAutocomplete>() {
            @Override
            public void onResponse(@NonNull Call<RootAutocomplete> call, @NonNull Response<RootAutocomplete> response) {
                Log.d("lol searchrepo", ""+response.body());
                Log.d("lol searchrepo", ""+response.message());
                Log.d("lol searchrepo", ""+response.toString());
                if (response.body() != null){

                    Log.d("lol searchrepo", ""+response.body());
                    Log.d("lol searchrepo", ""+response.body().getPredictions());
                    getListFromDetails(response.body().getPredictions());


                    //listOfPlace.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RootAutocomplete> call, Throwable t) {

            }
        });
    }

    private void getListFromDetails(List<Prediction> predictions) {
        List<Result> listDetail = new ArrayList<>();
        for (Prediction p : predictions){
            Map<String, String> params = new HashMap<String, String>();
            params.put("place_id", p.getPlaceId());
            params.put("key", apiKey);
            Call<RootDetails> placeDetails = mapsInterface.getDetailsPlace(params);
            placeDetails.enqueue(new Callback<RootDetails>() {
                @Override
                public void onResponse(@NonNull Call<RootDetails> call, @NonNull Response<RootDetails> response) {
                    ResultDetails obj = response.body().getResult();
                    Result newObj = new Result();
                    //Result newObj = Result.class.cast(obj);
                    newObj.setPlace_id(obj.getPlaceId());
                    newObj.setName(obj.getName());
                    newObj.setGeometry(obj.getGeometry());
                    newObj.setVicinity(obj.getVicinity());
                    newObj.setRating(obj.getRating());
                    newObj.setPhotos(obj.getPhotos());
                    newObj.setOpening_hours(obj.getOpeningHours());
                    listDetail.add(newObj);
                    listOfPlace.setValue(listDetail);
                }

                @Override
                public void onFailure(@NonNull Call<RootDetails> call, @NonNull Throwable t) {
                    Log.d("lol details fail", ""+t );
                }
            });
        }
    }
}
