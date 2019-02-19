package com.example.patryk.korko.containers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patryk on 2017-04-24.
 */

public class BoxesContainer {
    List<String> name;
    List<Boolean> value;

    public BoxesContainer() {
        name = new ArrayList<>();
        value = new ArrayList<>();
    }

    public void add(String name, boolean value) {
        this.name.add(name);
        this.value.add(value);
    }

    public boolean getValue(String name) {
        int n = this.name.indexOf(name);
        return value.get(n);
    }
    public String getName(int i) {
        return name.get(i);
    }
    public boolean getValue(int i) {
        return value.get(i);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof BoxesContainer){
            BoxesContainer b = (BoxesContainer) o;
            for(int i = 0; i < this.name.size() ; i++){
                if(!((this.name.get(i)).equals(b.getName(i))))
                    return false;
            }
            for(int i = 0; i < this.value.size() ; i++){
                if(!((this.value.get(i)).equals(b.getValue(i))))
                    return false;
            }
            return true;
        }
        return false;
    }
}
