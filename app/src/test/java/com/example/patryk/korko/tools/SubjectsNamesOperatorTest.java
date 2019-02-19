package com.example.patryk.korko.tools;

import org.junit.Before;
import org.junit.Test;

import static com.example.patryk.korko.tools.SubjectsNamesOperator.parseSubjectsToEnglish;
import static com.example.patryk.korko.tools.SubjectsNamesOperator.parseSubjectsToPolish;
import static org.junit.Assert.*;

/**
 * Created by Patryk on 2017-05-29.
 */
public class SubjectsNamesOperatorTest {
    String[] subjects = {"polish","maths","biology","history","english","wos","physics","chemistry","geography"};
    String[] subjectsP = {"Polski","Matematyka","Biologia","Historia","Angielski","WOS","Fizyka","Chemia","Geografia"};

    @Test
    public void testParseSubjectsToPolish() throws Exception {
        for(int i = 0; i <9; i++){
            assertEquals(parseSubjectsToPolish(subjects[i]),subjectsP[i]);
        }
    }

    @Test
    public void testPSubjectsToEnglish() throws Exception {
        for(int i = 0; i <9; i++){
            assertEquals(parseSubjectsToEnglish(subjectsP[i]),subjects[i]);
        }
    }

}