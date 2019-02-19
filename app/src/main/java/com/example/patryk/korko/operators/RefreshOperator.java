package com.example.patryk.korko.operators;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.patryk.korko.MyListRecyclerViewAdapter;
import com.example.patryk.korko.R;
import com.example.patryk.korko.containers.SearchValuesContainer;
import com.example.patryk.korko.dummy.DummyContent;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Patryk on 2017-05-12.
 */

public class RefreshOperator {
    private boolean mapUpToDate;
    private boolean listUpToDate;
    private LatLng[] positionArray;
    private List<DummyContent.DummyItem> list;
    private int area;

    public void refreshMap() {
        Context context = ViewsSingleton.getInstance().getAppContext();
        LatLng location = new LatLng(MapAndLocalizationSingleton.getInstance().getLocation().getLatitude(),
                MapAndLocalizationSingleton.getInstance().getLocation().getLongitude());
        GoogleMap googleMap = MapAndLocalizationSingleton.getInstance().getGoogleMap();
        if (googleMap != null) {
            googleMap.clear();
            CameraPosition position = CameraPosition.builder().target(location).zoom(10).bearing(0).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ViewsSingleton.getInstance().getMapView().findViewById(R.id.bottomSheetLayout));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                googleMap.setMyLocationEnabled(false);
            } else {
                googleMap.setMyLocationEnabled(true);
            }
            googleMap.addCircle(new CircleOptions()
                    .center(location)
                    .radius(area * 1000)
                    .strokeColor(Color.RED)
                    .strokeWidth(2.0f)
                    .fillColor(Color.parseColor("#5081c300"))
            );

            for (int i = 0; i < positionArray.length; i++) {
                if (positionArray[i] != null) {
                    try{
                        googleMap.addMarker(new MarkerOptions().position(positionArray[i]).title(list.get(i).name).snippet(list.get(i).email));
                    } catch (Exception e){
                            //do nothing
                    }

                }
            }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {
                    FacadeSingleton.getInstance().getFacade().showBottomSheet("map", arg0.getSnippet());
                    return true;
                }

            });
        }
    }

    public void refreshList() {
        try {
            Context context = ViewsSingleton.getInstance().getAppContext();
            RecyclerView recyclerView = (RecyclerView) ViewsSingleton.getInstance().getListView().findViewById(R.id.list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setAdapter(new MyListRecyclerViewAdapter(list, ViewsSingleton.getInstance().getListListener()));
        } catch (NullPointerException e) {
            Log.e("refreshList", "NullPointerException");
        }
    }

    public void update(LatLng[] positionArray, List<DummyContent.DummyItem> list, SearchValuesContainer searchValuesContainer) {
        this.list = list;
        this.positionArray = positionArray;
        area = searchValuesContainer.getArea();
    }

    public boolean isMapUpToDate() {
        return mapUpToDate;
    }

    public void setMapUpToDate(boolean mapUpToDate) {
        this.mapUpToDate = mapUpToDate;
    }

    public boolean isListUpToDate() {
        return listUpToDate;
    }

    public void setListUpToDate(boolean listUpToDate) {
        this.listUpToDate = listUpToDate;
    }

}
