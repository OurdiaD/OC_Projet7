package com.openclassrooms.go4lunch.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.openclassrooms.go4lunch.BuildConfig;
import com.openclassrooms.go4lunch.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    LatLng currentLatLng;
    Location curentLocation;
    String reponsePlace;


    public void setLatLng(Location location){
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public String getReponsePlace(){
        if (reponsePlace == null){
            requestPlace();
        }
        return reponsePlace;
    }

    public void requestPlace()  {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                //String location ;
                Resources res = Resources.getSystem();
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
                    reponsePlace = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
