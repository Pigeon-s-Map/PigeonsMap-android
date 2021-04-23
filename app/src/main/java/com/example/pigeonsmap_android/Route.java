package com.example.pigeonsmap_android;

import java.util.List;

public class Route {
    boolean circular;
    List<Location> locations;
    double length;
    int noOfLocations;
    List<Street> streets;
    List<Warning> warnings;

    public boolean calculate()
    {
        
        return false;
    }

    public void addLocation(Location location)
    {
        locations.add(location);
    }
    public void removeLocation(Location location)
    {
        locations.remove(location);
    }

    public boolean update()
    {

        return false;
    }

}

