package com.example.patryk.korko.containers;

/**
 * Created by Patryk on 2017-05-13.
 */

public class OpinionContainer {
    String opinion;
    String imgURL;
    double rating;
    String name;
    boolean nextOpinion;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isNextOpinion() {
        return nextOpinion;
    }

    public void setNextOpinion(boolean nextOpinion) {
        this.nextOpinion = nextOpinion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
