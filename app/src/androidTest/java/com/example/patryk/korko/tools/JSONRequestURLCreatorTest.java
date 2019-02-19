package com.example.patryk.korko.tools;

import android.location.Location;

import com.example.patryk.korko.containers.BoxesContainer;
import com.example.patryk.korko.containers.SearchValuesContainer;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Patryk on 2017-05-30.
 */
public class JSONRequestURLCreatorTest {
    SearchValuesContainer searchValuesContainer;
    @Before
    public void setUp() throws Exception {
        Location loc = new Location("Ala");
        loc.setLatitude(100d);
        loc.setLongitude(10d);
        MapAndLocalizationSingleton.getInstance().setLocation(loc);
        searchValuesContainer = new SearchValuesContainer();
        searchValuesContainer.setArea(1000);
        searchValuesContainer.setPrice(50);
        BoxesContainer sbBox =  new BoxesContainer();
        sbBox.add("Wiedza o społeczeństwie", true);
        sbBox.add("Biologia", true);
        sbBox.add("J. angielski", true);
        sbBox.add("Matematyka", true);
        sbBox.add("Fizyka", true);
        sbBox.add("Chemia", true);
        sbBox.add("J. polski", false);
        sbBox.add("Geografia", true);
        sbBox.add("Historia", true);

        BoxesContainer lvBox =  new BoxesContainer();
        lvBox.add("Szkoła podstawowa", true);
        lvBox.add("Gimnazjum", true);
        lvBox.add("Liceum", true);
        lvBox.add("Studia", false);
        searchValuesContainer.setLevelBoxesContainer(lvBox);
        searchValuesContainer.setSubjectsBoxesContainer(sbBox);
    }
    @Test
    public void testCreateJSONSearchRequestURL() throws Exception {
        JSONRequestURLCreator juc = new JSONRequestURLCreator();
        juc.createJSONSearchRequestURL(searchValuesContainer);
        String res = juc.getJsonSearchRequestURL();
        assertNotNull(res);

        assertEquals(res.contains("&subjects="),true);
        assertEquals(res.contains("&lattitude="),true);
        assertEquals(res.contains("&radius="+1000),true);
        assertEquals(res.contains("?longitude="),true);
        assertEquals(res.contains("&highEnd="+50),true);
        assertEquals(res.contains("?longitude="),true);
        assertEquals(res.contains("wos"),true);
        assertEquals(res.contains("secondaryPrice"),true);
        assertEquals(res.contains("uniPrice"),false);
        assertEquals(res.contains("polish"),false);
    }
}
