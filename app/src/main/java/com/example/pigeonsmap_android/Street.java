package com.example.pigeonsmap_android;

public class Street {
    int rate;
    String name;

    public Street()
    {
        this.rate = 0;
        this.name = null;
    }

    public Street(int rate, String name)
    {
        this.rate = rate;
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
