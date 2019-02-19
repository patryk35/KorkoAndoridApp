package com.example.patryk.korko.patterns;

import android.content.Context;
import android.view.View;

import com.example.patryk.korko.activities.searchFragments.ListFragment;

/**
 * Created by Patryk on 2017-05-15.
 */

public class ViewsSingleton {
    private static ViewsSingleton ourInstance = null;
    public static synchronized ViewsSingleton getInstance(){
        if(ourInstance == null)
            ourInstance = new ViewsSingleton();
        return  ourInstance;
    }
    private ViewsSingleton(){
        //empty constructor
    }

    private View launcherView;
    private View listView;
    private View mapView;
    private View searchFragmentView;
    private static ListFragment.OnListFragmentInteractionListener listListener;
    private Context appContext;
    private  View searchView;

    public View getLauncherView() {
        return launcherView;
    }

    public void setLauncherView(View launcherView) {
        this.launcherView = launcherView;
    }

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public View getListView() {
        return listView;
    }

    public void setListView(View listView) {
        this.listView = listView;
    }

    public ListFragment.OnListFragmentInteractionListener getListListener() {
        return listListener;
    }

    public void setListListener(ListFragment.OnListFragmentInteractionListener listListener) {
        ViewsSingleton.listListener = listListener;
    }

    public View getMapView() {
        return mapView;
    }

    public void setMapView(View mapView) {
        this.mapView = mapView;
    }

    public View getSearchFragmentView() {
        return searchFragmentView;
    }

    public void setSearchFragmentView(View searchFragmentView) {
        this.searchFragmentView = searchFragmentView;
    }

    public View getSearchView() {
        return searchView;
    }

    public void setSearchView(View searchView) {
        this.searchView = searchView;
    }
}
