package com.example.patryk.korko.operators;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Patryk on 2017-05-29.
 */
public class HttpHandlerTest {
    @Test
    public void testMakeCall() throws Exception {
        HttpHandler hh = new HttpHandler();
        String url = "http://korko.cf:3000/api/v1/coach/searchCategories";
        String result = hh.makeCall(url);
        assertNotNull(result);
    }

}