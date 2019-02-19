package com.example.patryk.korko.containers;

/**
 * Created by Patryk on 2017-05-12.
 */

public class SearchValuesContainer {
    private BoxesContainer subjectsBoxesContainer;
    private BoxesContainer levelBoxesContainer;
    private int price;
    private int area;

    public BoxesContainer getSubjectsBoxesContainer() {
        return subjectsBoxesContainer;
    }

    public void setSubjectsBoxesContainer(BoxesContainer subjectsBoxesContainer) {
        this.subjectsBoxesContainer = subjectsBoxesContainer;
    }

    public BoxesContainer getLevelBoxesContainer() {
        return levelBoxesContainer;
    }

    public void setLevelBoxesContainer(BoxesContainer levelBoxesContainer) {
        this.levelBoxesContainer = levelBoxesContainer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof SearchValuesContainer){
            SearchValuesContainer s = (SearchValuesContainer)o;
            if(this.price == s.getPrice() && this.area == s.getArea()
                    && this.levelBoxesContainer.equals(s.getLevelBoxesContainer())
                    && this.subjectsBoxesContainer.equals(s.getSubjectsBoxesContainer()));
            return true;
        }
        return false;
    }
}
