package com.example.patryk.korko.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static com.example.patryk.korko.appearance.ButtonsLookChanger.buttonAddProperties;

public class Launcher extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private GoogleApiClient mGoogleApiClient;
    private CheckBox checkBoxLoc;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setUpValues();
        FacadeSingleton.getInstance().getFacade().setUpApplication();
        refreshButtons();
        setUpView();
    }

    private void refreshButtons() {
        buttonAddProperties((Button) findViewById(R.id.launcherButton), -1);
        buttonAddProperties((Button) findViewById(R.id.locButton), -1);
    }

    public void setUpValues() {
        ViewsSingleton.getInstance().setAppContext(this.getApplicationContext());
        ViewsSingleton.getInstance().setLauncherView(this.findViewById(android.R.id.content));
        sp = getSharedPreferences("pl.korko.Kqrko", Context.MODE_PRIVATE);
        spEditor = sp.edit();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        checkBoxLoc = (CheckBox) findViewById(R.id.checkBoxLoc);
        textView = (TextView) findViewById(R.id.citiesList);
    }

    public void setUpView() {
        //If somebody wants to change city
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int AppStatus = extras.getInt("AppStatus");
            if (AppStatus == 1) {
                TextView HelloTextView = (TextView) findViewById(R.id.helloTextView);
                HelloTextView.setVisibility(View.INVISIBLE);
                spEditor.remove("doIt").commit();
            }
        }
        final String lat = sp.getString("lat", null);
        final String lng = sp.getString("lng", null);
        if (sp.getString("doIt", null) == null || lat == null || lng == null) {
            findViewById(R.id.launcherLogo).setVisibility(View.VISIBLE);
            findViewById(R.id.mainPartOfLauncherLayout).setBackground(new ColorDrawable(Color.argb(20, 255, 255, 255)));
            findViewById(R.id.helloTextView).setVisibility(View.VISIBLE);
            findViewById(R.id.launcherButton).setVisibility(View.VISIBLE);
            findViewById(R.id.locButton).setVisibility(View.VISIBLE);
            checkBoxLoc.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.mainPartOfLauncherLayout).setBackground(new ColorDrawable(Color.argb(0, 68, 145, 226)));
            findViewById(R.id.launcherLogo2).setVisibility(View.VISIBLE);
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        Log.d("Launcher.setUpView", "Sleep interrupted");
                    } finally {
                        Location location = new Location("city");
                        location.setLongitude(Double.parseDouble(lng));
                        location.setLatitude(Double.parseDouble(lat));
                        MapAndLocalizationSingleton.getInstance().setLocation(location);
                        Intent intent = new Intent(".MainMenu");
                        startActivity(intent);
                    }
                }
            };
            thread.start();
        }
    }

    public void setCityByName(View view) {

        if (FacadeSingleton.getInstance().getFacade().setUserCityByName()) {
            /*Save location in SharedPreferences even when user don't want it, cause
            it is used to reload locatin, after long staying application in onPause */
            Location loc = MapAndLocalizationSingleton.getInstance().getLocation();
            spEditor.putString("lat", loc.getLatitude() + "");
            spEditor.putString("lng", loc.getLongitude() + "");
            if (checkBoxLoc.isChecked()) {
                spEditor.putString("doIt", "true");
                spEditor.commit();
            }
            Intent intent = new Intent(".MainMenu");
            startActivity(intent);
        } else {
            Toast.makeText(this, "Niepoprawna nazwa miasta lub miasto nie leży w Polsce!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCityByLoc(View view) {
        mGoogleApiClient.connect();

        Thread thread = new Thread() {
            public void run() {
                try {
                    while (MapAndLocalizationSingleton.getInstance().getLocation() == null) ;
                    sleep(100);
                } catch (InterruptedException e) {
                    Log.d("Launcher.setCityByLoc", "Sleep interrupted");
                } finally {
                    Location location = MapAndLocalizationSingleton.getInstance().getLocation();
                    if (location != null) {
                        /*Save location in SharedPreferences even when user don't want it, cause
                        it is used to reload locatin, after long staying application in onPause */
                        spEditor.putString("lat", location.getLatitude() + "");
                        spEditor.putString("lng", location.getLongitude() + "");
                        MapAndLocalizationSingleton.getInstance().setLocation(location);
                        if (checkBoxLoc.isChecked()) {
                            spEditor.putString("doIt", "true");
                            spEditor.commit();
                        }
                        Intent intent = new Intent(".MainMenu");
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Ustaw odpowienie uprawnienia, by uzyskać " +
                    "dokładne położenie lub kontynuj wprowadzają nazwę miasta", Toast.LENGTH_LONG).show();
        } else {
            MapAndLocalizationSingleton.getInstance().setLocation(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        super.onStop();
    }
}


