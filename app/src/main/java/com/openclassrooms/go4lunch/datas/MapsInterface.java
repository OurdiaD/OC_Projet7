package com.openclassrooms.go4lunch.datas;

import com.openclassrooms.go4lunch.models.maps.RootAutocomplete;
import com.openclassrooms.go4lunch.models.maps.RootDetails;
import com.openclassrooms.go4lunch.models.maps.RootList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MapsInterface {

    @GET("api/place/nearbysearch/json")
    Call<RootList> getAllPlaces(@QueryMap Map<String, String> params);

    @GET("api/place/details/json")
    Call<RootDetails> getDetailsPlace(@QueryMap Map<String, String> params);

    @GET("api/place/autocomplete/json")
    Call<RootAutocomplete> searchPlaces(@QueryMap Map<String, String> params);
}
