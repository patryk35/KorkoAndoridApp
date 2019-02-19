package com.example.patryk.korko.operators;

import android.graphics.Color;
import android.location.Location;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patryk on 2017-05-23.
 */

public class LauncherOperator {
    private String jsonStr;
    private String[] records;
    private Double lat;
    private Double lng;
    private String country;

    public boolean setCityByName() {
        View view = ViewsSingleton.getInstance().getLauncherView();
        AutoCompleteTextView city = (AutoCompleteTextView) view.findViewById(R.id.citiesList);
        city.setDrawingCacheBackgroundColor(Color.BLACK);
        String city_name = city.getText().toString().trim();
        records = city_name.split("\\s+");
        LocThread locThread = new LocThread();
        locThread.start();
        long startTime = System.currentTimeMillis();
        long currentWaitTime = 0;
        while (jsonStr == null && currentWaitTime < 1000)
            currentWaitTime = System.currentTimeMillis() - startTime;
        if (jsonStr != null) {
            if (JSONGettingData()) {
                Location location = new Location("city");
                location.setLatitude(lat);
                location.setLongitude(lng);
                MapAndLocalizationSingleton.getInstance().setLocation(location);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    private boolean JSONGettingData(){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray results = jsonObj.getJSONArray("results");
            JSONObject r = results.getJSONObject(0);
            JSONObject geometry = r.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            JSONArray adress = r.getJSONArray("address_components");
            JSONObject countryObject = adress.getJSONObject(0);
            for (int j = 0; j <= r.length(); j++) {
                try {
                    if (((adress.getJSONObject(j)).getJSONArray("types")).getString(0).equals("country")) {
                        countryObject = adress.getJSONObject(j);
                    }
                } catch (JSONException e) {
                        /*do nothing, just not read lines with that exception*/
                }
            }
            country = countryObject.getString("short_name");
            lat = location.getDouble("lat");
            lng = location.getDouble("lng");
        } catch (final JSONException e) {
            Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Błąd usług Google. Spróbuj ponownie później!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return lng != -0 && country.equals("PL");
    }
    private class LocThread extends Thread {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
            for (int i = 0; i < records.length; i++) {
                if (!(records[i].equals(".") || records[i].equals(",") || records[i].equals(";"))) {
                    sb.append(records[i]);
                    if (i + 1 < records.length)
                        sb.append("+");
                }
            }
            String url = sb.toString();
            jsonStr = FacadeSingleton.getInstance().getFacade().httpMakeCall(url);
        }
    }
}
