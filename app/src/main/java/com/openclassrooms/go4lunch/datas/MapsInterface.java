package com.openclassrooms.go4lunch.datas;

import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.models.maps.Root;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MapsInterface {

    @GET("api/place/nearbysearch/json")
    Call<Root> getAllPlaces(@QueryMap Map<String, String> params);

    @GET("api/place/details/json")
    Call<Root> getDetailsPlace(@Query("place_id") String placeId);
}
