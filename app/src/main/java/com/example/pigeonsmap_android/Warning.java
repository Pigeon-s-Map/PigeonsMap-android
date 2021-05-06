package com.example.pigeonsmap_android;

import com.google.android.gms.maps.model.LatLng;

public class Warning {
    String text;
    String username;
    LatLng location;
    boolean isPrivate;
    int id;

    public Warning()
    {
        this.text=null;
        this.username=null;
        this.location = null;
        this.isPrivate = false;
    }

    public Warning(int id, String text, String username, LatLng location, boolean isPrivate )
    {
        this.id = id;
        this.text = text;
        this.username = username;
        this.location = location;
        this.isPrivate = false;
    }

    public String getText() {
        return text;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LatLng getLocation() {
        return location;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setID(int id){ this.id = id; }

}
