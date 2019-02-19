package com.example.patryk.korko.containers;

import android.location.Location;
import android.support.test.filters.SmallTest;

import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by Patryk on 2017-05-29.
 */
public class JSONAddingUserContainerTest {

    JSONAddingUserContainer container;
    String sName;

    @Before
    public void setUp() throws Exception {
        container = new JSONAddingUserContainer();
        sName = "Polski";
    }

    @Test
    public void testMakeStringJSON() throws Exception {
        container.addSubject(sName, "100", "200", "300", "400", 4);
        container.addSubject(sName, "100", "200", "300", null, 3);
        container.addSubject(sName, "100", "200", null, null, 2);
        container.setDescription("Ala");
        container.setEmail("e@e.pl");
        container.setFirstName("Pat");
        container.setLastName("Mil");
        container.setOauth("100");
        Location loc = new Location("Ala");
        loc.setLatitude(100d);
        loc.setLongitude(10d);
        MapAndLocalizationSingleton.getInstance().setLocation(loc);
        assertNotNull(container.makeStringJSON());
    }

    @Test
    public void testAddSubject() throws Exception {
        int counter = 14;
        container.addSubject(sName, "100", "200", "300", "400", 4);
        container.addSubject(sName, "100", "200", "300", null, 3);
        container.addSubject(sName, "100", "200", null, "400", 3);
        container.addSubject(sName, "100", null, "300", "400", 3);
        container.addSubject(sName, null, "200", "300", "400", 3);
        container.addSubject(sName, "100", "200", null, null, 2);
        container.addSubject(sName, "100", null, null, "400", 2);
        container.addSubject(sName, null, null, "300", "400", 2);
        container.addSubject(sName, null, "200", "300", null, 2);
        container.addSubject(sName, "100", null, null, null, 1);
        container.addSubject(sName, null, null, null, "400", 1);
        container.addSubject(sName, null, null, "300", null, 1);
        container.addSubject(sName, null, "200", null, null, 1);
        container.addSubject(sName, null, null, null, null, 0);
        assertEquals(container.getSubjectsListSize(),counter);
    }

    @Test
    public void testCheckIsItSetted() throws Exception {
        container.addSubject(sName, "100", "200", "300", "400", 4);
        if(!container.checkIsItSetted(sName)){
            container.addSubject(sName, "100", "200", "300", "400", 4);
        }
        container.addSubject("Angielski", "100", "200", "300", "400", 4);
        if(!container.checkIsItSetted("WOS")){
            container.addSubject("WOS", "100", "200", "300", "400", 4);
        }
        assertEquals(container.getSubjectsListSize(),3);
    }

}