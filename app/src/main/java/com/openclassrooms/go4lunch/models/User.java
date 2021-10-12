package com.openclassrooms.go4lunch.models;

public class User {

    private String userId;
    private String fullname;
    private String email;

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
}
