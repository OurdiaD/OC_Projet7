package com.openclassrooms.go4lunch.models;


import java.util.Date;

public class Message {
    private String text;
    private User user;
    private Date dateCreated;

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
        this.dateCreated = new Date();
    }

    public Message(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
