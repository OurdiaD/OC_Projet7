package com.openclassrooms.go4lunch.ui.main;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class MainViewModel extends ViewModel {
    LatLng currentLatLng;
    Location currentLocation;
    String reponsePlace;
    MapsInterface mapsInterface;
    List<Result> listOfPlace;

    private static MainViewModel instance;

    public static MainViewModel getInstance() {
        if (instance == null) {
            synchronized (MainViewModel.class) {
                if (instance == null) {
                    instance = new MainViewModel();
                }
            }
        }
        return instance;
    }

    public MainViewModel(){
        Retrofit retro = RetrofitClient.getClient("https://maps.googleapis.com/maps/");
        mapsInterface = RetrofitClient.getInterface();
    }

    public void setLatLng(Location location){
        currentLocation = location;
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public List<Result> getReponsePlace(){
        if (listOfPlace == null){
            Log.d("lol mainviewmodel", "if null");
            requestPlace();
        }
        Log.d("lol mainviewmodel", "reponse");
        return listOfPlace;
    }

    public List<Result> requestPlace()  {
        /*Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String apiKey = BuildConfig.API_KEY;
                String requestUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
                        "location="+currentLatLng.latitude +","+currentLatLng.longitude+
                        "&radius=500&types=food&name=cruise&key="+apiKey;
                Request request = new Request.Builder()
                        .url(requestUrl)
                        .method("GET", null)
                        .build();

                Call client = new OkHttpClient().newCall(request);
                try {
                    Response response = client.execute();
                    Log.d("lol place", ""+response.body().string());
                    Log.d("lol place", ""+requestUrl);
                    reponsePlace = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        String apiKey = BuildConfig.API_KEY;
        String requestUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
                "location="+currentLatLng.latitude +","+currentLatLng.longitude+
                "&radius=500&types=food&name=cruise&key="+apiKey+"/";
        //Retrofit retro = RetrofitClient.getClient("https://maps.googleapis.com/maps/");
        Map<String, String> params = new HashMap<String, String>();
        params.put("location", currentLatLng.latitude +","+currentLatLng.longitude);
        params.put("radius", "500");
        params.put("types", "food");
        params.put("name", "cruise");
        params.put("key", apiKey);
        Log.d("lol mainvm1", ""+ requestUrl);
        Call<Root> placesResult = mapsInterface.getAllPlaces(params);

        Log.d("lol mainvm2", ""+ placesResult.isExecuted());
        Log.d("lol mainvm3", ""+ placesResult.toString());
        placesResult.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root>response) {
                Log.d("lol response", "onrep" );
                if (response.body() != null){
                    //listOfPlace.setValue(response.body());
                    listOfPlace = response.body().getResults();
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

        return listOfPlace;

    }
}
