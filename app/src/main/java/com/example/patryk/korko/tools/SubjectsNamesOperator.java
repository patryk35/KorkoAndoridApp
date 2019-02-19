package com.example.patryk.korko.tools;

/**
 * Created by Patryk on 2017-05-12.
 */

public class SubjectsNamesOperator {
    public static String parseSubjectsToPolish(String parseName) {
        if (parseName.equals("polish")) {
            return "Polski";
        } else if (parseName.equals("maths")) {
            return "Matematyka";
        } else if (parseName.equals("biology")) {
            return "Biologia";
        } else if (parseName.equals("history")) {
            return "Historia";
        } else if (parseName.equals("english")) {
            return "Angielski";
        } else if (parseName.equals("wos")) {
            return "WOS";
        } else if (parseName.equals("physics")) {
            return "Fizyka";
        } else if (parseName.equals("chemistry")) {
            return "Chemia";
        } else if (parseName.equals("geography")) {
            return "Geografia";
        } else {
            return "undefinied";
        }
    }
    public static String parseSubjectsToEnglish(String parseName) {
        if (parseName.equals("Polski")) {
            return "polish";
        } else if (parseName.equals("Matematyka")) {
            return "maths";
        } else if (parseName.equals("Biologia")) {
            return "biology";
        } else if (parseName.equals("Historia")) {
            return "history";
        } else if (parseName.equals("Angielski")) {
            return "english";
        } else if (parseName.equals("WOS")) {
            return "wos";
        } else if (parseName.equals("Fizyka")) {
            return "physics";
        } else if (parseName.equals("Chemia")) {
            return "chemistry";
        } else if (parseName.equals("Geografia")) {
            return "geography";
        } else {
            return "undefinied";
        }
    }
}
