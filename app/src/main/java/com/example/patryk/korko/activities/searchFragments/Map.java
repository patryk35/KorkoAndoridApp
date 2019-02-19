package com.example.patryk.korko.activities.searchFragments;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patryk.korko.R;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;


public class Map extends Fragment implements OnMapReadyCallback {

    private static GoogleMap mGoogleMap;
    private MapView mapView;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout));
        ViewsSingleton.getInstance().setMapView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        FacadeSingleton.getInstance().getFacade().checkSearch();
        FacadeSingleton.getInstance().getFacade().searchRecords();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        MapAndLocalizationSingleton.getInstance().setGoogleMap(mGoogleMap);
        FacadeSingleton.getInstance().getFacade().checkSearch();
        FacadeSingleton.getInstance().getFacade().searchRecords();
    }
}
