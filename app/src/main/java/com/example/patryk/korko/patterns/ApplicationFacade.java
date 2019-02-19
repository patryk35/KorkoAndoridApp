package com.example.patryk.korko.patterns;

import com.example.patryk.korko.dummy.DummyContent;
import com.example.patryk.korko.operators.BottomSheetOperator;
import com.example.patryk.korko.operators.HttpHandler;
import com.example.patryk.korko.operators.JSONDateBaseOperator;
import com.example.patryk.korko.operators.LauncherOperator;
import com.example.patryk.korko.operators.RefreshOperator;
import com.example.patryk.korko.operators.SearchFragmentLoader;
import com.example.patryk.korko.operators.SearchOperator;
import com.example.patryk.korko.tools.JSONRequestURLCreator;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Patryk on 2017-04-28.
 */

public class ApplicationFacade {
    private SearchFragmentLoader searchFragmentLoader;
    private SearchOperator searchOperator;
    private JSONDateBaseOperator jsonDateBaseOperator;
    private HttpHandler httpHandler;
    private BottomSheetOperator bottomSheetOperator;
    private JSONRequestURLCreator jsonRequestURLCreator;
    private RefreshOperator refreshOperator;
    private LauncherOperator launcherOperator;

    public void setUpApplication() {
        searchFragmentLoader = new SearchFragmentLoader();
        searchOperator = new SearchOperator();
        jsonDateBaseOperator = new JSONDateBaseOperator();
        httpHandler = new HttpHandler();
        bottomSheetOperator = new BottomSheetOperator();
        jsonRequestURLCreator = new JSONRequestURLCreator();
        refreshOperator = new RefreshOperator();
        launcherOperator = new LauncherOperator();
    }

    public void checkSearch() {
        searchOperator.checkArea();
        searchOperator.checkPrice();
        searchOperator.checkSubjectsBox();
        searchOperator.checkLevelsBox();
        searchOperator.checkValuesUpToDate();
        refreshOperator.setListUpToDate(false);
        refreshOperator.setMapUpToDate(false);
    }

    public void searchRecords() {
        if (searchOperator.isValuesUpToDate()) {
            jsonRequestURLCreator.createJSONSearchRequestURL(searchOperator.getSearchValuesContainer());
            jsonDateBaseOperator.searchInDateBase(jsonRequestURLCreator.getJsonSearchRequestURL());
        }

    }

    public void setUpSearchView() {
        searchFragmentLoader.setAreaSeekBar();
        searchFragmentLoader.setPriceSeekBar();
    }

    public void updateRefreshData(LatLng[] positionArray, List<DummyContent.DummyItem> list) {
        refreshOperator.update(positionArray, list, searchOperator.getSearchValuesContainer());
    }

    public void updateMap() {
        if (!refreshOperator.isMapUpToDate()) {
            refreshOperator.refreshMap();
            refreshOperator.setMapUpToDate(true);
        }
    }

    public void updateList() {
        if (!refreshOperator.isListUpToDate()) {
            refreshOperator.refreshList();
            refreshOperator.setListUpToDate(true);
        }
    }

    public String httpMakeCall(String url) {
        return httpHandler.makeCall(url);
    }

    public void showBottomSheet(String type, String url) {
        bottomSheetOperator.setUpBottomSheet(type, url);
    }

    public boolean setUserCityByName(){
        return launcherOperator.setCityByName();
    }
}

