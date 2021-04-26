package com.example.pigeonsmap_android;

import java.util.List;

public class Route {
    boolean circular;
    List<Location> locations;
    double length;
    int noOfLocations;
    List<Street> streets;
    List<Warning> warnings;

    public Route()
    {
        this.locations = null;
        this.circular = false;
        this.length = 0;
        this.noOfLocations = 0;
        this.streets = null;
        this.warnings = null;
    }

    public Route(boolean circular, List<Location> locations, double length, int noOfLocations
                ,List<Street> streets, List<Warning> warnings)
    {
        this.locations = locations;
        this.circular = circular;
        this.length = length;
        this.noOfLocations = noOfLocations;
        this.streets = streets;
        this.warnings = warnings;
    }

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

