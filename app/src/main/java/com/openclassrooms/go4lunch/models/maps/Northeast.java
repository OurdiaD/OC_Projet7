package com.openclassrooms.go4lunch.models.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Northeast {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
