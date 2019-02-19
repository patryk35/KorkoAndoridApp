package com.example.patryk.korko.containers;

import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;

import java.util.ArrayList;

import static com.example.patryk.korko.tools.SubjectsNamesOperator.parseSubjectsToEnglish;

/**
 * Created by Patryk on 2017-05-21.
 */

public class JSONAddingUserContainer {
    private String firstName;
    private String lastName;
    private String email;
    private String oauth;
    private String descritpion;
    private ArrayList<String> subjectsList = new ArrayList<>();
    private ArrayList<String> subjectsControlList = new ArrayList<>();


    public String makeStringJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"profile\": {");
        sb.append("\"name\":").append("\""+firstName+"\"").append(",");
        sb.append("\"surname\":").append("\""+lastName+"\"").append(",");
        sb.append("\"email\":").append("\""+email+"\"").append(",");
        sb.append("\"oauth\":").append("\""+oauth+"\"");
        sb.append("},");
        sb.append("\"description\": {");
        sb.append("\"about\":").append("\""+descritpion+"\"").append(",");
        sb.append("\"subjects\": [");
        for (int i = 0; i < subjectsList.size(); i++) {
            sb.append(subjectsList.get(i));
            if (i + 1 != subjectsList.size()) {
                sb.append(",");
            } else {
                sb.append("]");
            }
        }
        sb.append("},");
        sb.append("\"loc\": {");
        sb.append("\"geometry\": {");
        sb.append("\"type\": \"Point\",");
        sb.append("\"coordinates\": [").append(MapAndLocalizationSingleton.getInstance().getLocation().getLongitude());
        sb.append(",");
        sb.append(MapAndLocalizationSingleton.getInstance().getLocation().getLatitude()).append("]");
        sb.append("},");
        sb.append("\"properties\": {");
        sb.append("\"name\": ").append("\""+firstName + lastName+"\"");
        sb.append("}");
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }

    public void addSubject(String name, String primaryPrice, String secondaryPrice, String highschoolPrice, String uniPrice, int counter) {
        StringBuilder sb = new StringBuilder();
        int myCounter = 0;
        sb.append("{");
        sb.append("\"name\":").append("\""+parseSubjectsToEnglish(name)+"\"").append(",");
        if (primaryPrice != null) {
            sb.append("\"primaryPrice\":").append(primaryPrice);
            if (++myCounter < counter) {
                sb.append(",");
            }
        } else if (primaryPrice != null) {
            sb.append("\"secondaryPrice\":").append(secondaryPrice);
            if (++myCounter < counter) {
                sb.append(",");
            }
        } else if (primaryPrice != null) {
            sb.append("\"highschoolPrice\":").append(highschoolPrice);
            if (++myCounter < counter) {
                sb.append(",");
            }
        } else if (primaryPrice != null) {
            sb.append("\"uniPrice\":").append(uniPrice);
            if (++myCounter < counter) {
                sb.append(",");
            }
        }
        sb.append("}");
        subjectsList.add(sb.toString());
        subjectsControlList.add(name);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public void setDescription(String descritpion) {
        this.descritpion = descritpion;
    }

    public int getSubjectsListSize() {
        return subjectsList.size();
    }

    public boolean checkIsItSetted(String name) {
        for (int i = 0; i < subjectsControlList.size(); i++) {
            if (subjectsControlList.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }
}
