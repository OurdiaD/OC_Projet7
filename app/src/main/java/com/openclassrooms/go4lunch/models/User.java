package com.openclassrooms.go4lunch.models;

import android.net.Uri;

import com.google.firebase.Timestamp;

import org.jetbrains.annotations.Nullable;

public class User {

    private String userId;
    private String fullname;
    private String email;
    private String photoUrl;
    private String placeId;
    private String placeName;
    private String placeAddress;
    private Timestamp timestamp;

    public User(String userId, String fullname, String email, @Nullable String photoUrl) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public User(String userId, String fullname, String email) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
    }

    public User(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
