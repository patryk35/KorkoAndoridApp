package com.example.patryk.korko.patterns;

/**
 * Created by Patryk on 2017-04-17.
 */

public class FacadeSingleton {
    private static FacadeSingleton ourInstance = null;

    //Values which are hold in Singleton
    private ApplicationFacade Facade;

    public static synchronized FacadeSingleton getInstance() {
        if(null == ourInstance)
            ourInstance= new FacadeSingleton();
        return ourInstance;
    }
    private FacadeSingleton() {
        Facade = new ApplicationFacade();
    }
    public ApplicationFacade getFacade(){
        return Facade;
    }
}
