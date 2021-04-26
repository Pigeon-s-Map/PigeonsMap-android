package com.example.pigeonsmap_android;

public class Warning {
    String text;
    User user;
    Location location;
    boolean isPrivate;

    public Warning()
    {
        this.text=null;
        this.user=null;
        this.location = null;
        this.isPrivate = false;
    }

    public Warning(String text, User user, Location location, boolean isPrivate )
    {
        this.text = text;
        this.user = user;
        this.location = location;
        this.isPrivate = false;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }


}
