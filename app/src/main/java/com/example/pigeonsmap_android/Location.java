package com.example.pigeonsmap_android;

public class Location {
    double xCoordinate;
    double yCoordinate;
    Street street;

    public Location()
    {
        xCoordinate = 0;
        yCoordinate = 0;
        street = null;
    }

    public Location(double xCoordinate, double yCoordinate,Street street)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.street = street;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }


}
