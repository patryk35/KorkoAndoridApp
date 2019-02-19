package com.example.patryk.korko.tools;

import android.util.Log;

import com.example.patryk.korko.containers.BoxesContainer;
import com.example.patryk.korko.containers.SearchValuesContainer;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;

/**
 * Created by Patryk on 2017-05-12.
 */

public class JSONRequestURLCreator {
    private BoxesContainer subjectsBoxes;
    private BoxesContainer levelsBox;
    private String jsonSearchRequestURL;
    public void createJSONSearchRequestURL(SearchValuesContainer searchValuesContainer){
        int price = searchValuesContainer.getPrice();
        int area = searchValuesContainer.getArea();
        subjectsBoxes = searchValuesContainer.getSubjectsBoxesContainer();
        levelsBox = searchValuesContainer.getLevelBoxesContainer();
        StringBuilder urlStringBuilder = new StringBuilder();
        urlStringBuilder.append("http://korko.cf:3000/api/v1/coach/searchCategories");
        urlStringBuilder.append("?longitude=").append(MapAndLocalizationSingleton.getInstance().getLocation().getLongitude());
        urlStringBuilder.append("&lattitude=").append(MapAndLocalizationSingleton.getInstance().getLocation().getLatitude());
        urlStringBuilder.append("&radius=").append(area);

        urlStringBuilder.append(subjectSearchParser("Matematyka", "maths"));
        urlStringBuilder.append(subjectSearchParser("Fizyka", "physics"));
        urlStringBuilder.append(subjectSearchParser("Chemia", "chemistry"));
        urlStringBuilder.append(subjectSearchParser("J. polski", "polish"));
        urlStringBuilder.append(subjectSearchParser("Biologia", "biology"));
        urlStringBuilder.append(subjectSearchParser("Geografia", "geography"));
        urlStringBuilder.append(subjectSearchParser("Historia", "history"));
        urlStringBuilder.append(subjectSearchParser("Wiedza o społeczeństwie", "wos"));
        urlStringBuilder.append(subjectSearchParser("J. angielski", "english"));

        urlStringBuilder.append(levelSearchParser("Szkoła podstawowa", "primaryPrice"));
        urlStringBuilder.append(levelSearchParser("Gimnazjum", "secondaryPrice"));
        urlStringBuilder.append(levelSearchParser("Liceum", "highschoolPrice"));
        urlStringBuilder.append(levelSearchParser("Studia", "uniPrice"));
        urlStringBuilder.append("&lowEnd=0");
        urlStringBuilder.append("&highEnd=").append(price * 10);
        jsonSearchRequestURL =  urlStringBuilder.toString();
    }
    private String subjectSearchParser(String whatSubject, String nameJSON) {
        if (subjectsBoxes.getValue(whatSubject) == true) {
            StringBuilder sb = new StringBuilder();
            return sb.append("&subjects=").append(nameJSON).toString();
        }
        return "";
    }

    private String levelSearchParser(String whatLevel, String nameJSON) {
        if (levelsBox.getValue(whatLevel) == true) {
            StringBuilder sb = new StringBuilder();
            sb.append("&level=").append(nameJSON);
            return sb.toString();
        }
        return "";
    }

    public String getJsonSearchRequestURL() {
        return jsonSearchRequestURL;
    }
}
