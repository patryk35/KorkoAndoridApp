package com.example.patryk.korko.patterns;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Patryk on 2017-05-15.
 */

public class MapAndLocalizationSingleton {
    private static MapAndLocalizationSingleton ourInstance = null;
    public static synchronized MapAndLocalizationSingleton getInstance(){
        if(ourInstance == null)
            ourInstance = new MapAndLocalizationSingleton();
        return  ourInstance;
    }
    private MapAndLocalizationSingleton(){
        //empty constructor
    }

    private static GoogleMap googleMap;
    private static Location location;

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        MapAndLocalizationSingleton.googleMap = googleMap;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        MapAndLocalizationSingleton.location = location;
    }
}