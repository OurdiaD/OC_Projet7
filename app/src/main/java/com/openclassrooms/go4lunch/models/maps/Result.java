package com.openclassrooms.go4lunch.models.maps;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.openclassrooms.go4lunch.models.User;

import java.util.List;

public class Result {
    @SerializedName("business_status")
    @Expose
    public String business_status;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("icon_background_color")
    @Expose
    public String icon_background_color;
    @SerializedName("icon_mask_base_uri")
    @Expose
    public String icon_mask_base_uri;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("opening_hours")
    @Expose
    public OpeningHours opening_hours;
    @SerializedName("photos")
    @Expose
    public List<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    public String place_id;
    @SerializedName("plus_code")
    @Expose
    public PlusCode plus_code;
    @SerializedName("rating")
    @Expose
    public Float rating;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("types")
    @Expose
    public List<String> types = null;
    @SerializedName("user_ratings_total")
    @Expose
    public Integer user_ratings_total;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;

    private MutableLiveData<List<User>> listUser;

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_background_color() {
        return icon_background_color;
    }

    public void setIcon_background_color(String icon_background_color) {
        this.icon_background_color = icon_background_color;
    }

    public String getIcon_mask_base_uri() {
        return icon_mask_base_uri;
    }

    public void setIcon_mask_base_uri(String icon_mask_base_uri) {
        this.icon_mask_base_uri = icon_mask_base_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public MutableLiveData<List<User>> getListUser() {
        return listUser;
    }

    public void setListUser(MutableLiveData<List<User>> listUser) {
        this.listUser = listUser;
    }
}
