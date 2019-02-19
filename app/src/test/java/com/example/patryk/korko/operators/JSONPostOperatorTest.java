package com.example.patryk.korko.operators;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Patryk on 2017-05-29.
 */
public class JSONPostOperatorTest {
    @Test
    public void testMakeRequest() throws Exception {
        String uri ="http://korko.cf:3000/api/v1/coach/new";
        String json = "{\n" +
                "        \"profile\": {\n" +
                "            \"name\": \"Patryk\",\n" +
                "            \"surname\": \"Milewskia\",\n" +
                "            \"email\": \"pat35a@op.pl\",\n" +
                "            \"oauth\": \"914431778578699\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"about\": \"Would not employ myself as a korepetytor\",\n" +
                "            \"subjects\": [{\n" +
                "                \"name\": \"biology\",\n" +
                "                \"primaryPrice\": 50,\n" +
                "                \"secondaryPrice\": 60,\n" +
                "                \"highschoolPrice\": 100,\n" +
                "                \"uniPrice\": 10\n" +
                "            }, {\n" +
                "                \"name\": \"english\",\n" +
                "                \"secondaryPrice\": 60,\n" +
                "                \"highschoolPrice\": 100,\n" +
                "                \"uniPrice\": 10\n" +
                "            }]\n" +
                "        },\n" +
                "        \"loc\": {\n" +
                "            \"geometry\": {\n" +
                "                \"type\": \"Point\",\n" +
                "                \"coordinates\": [20.979666,52.190675]\n" +
                "            },\n" +
                "            \"properties\": {\n" +
                "                \"name\": \"Warsaw\"\n" +
                "            }\n" +
                "\n" +
                "        },\n" +
                "        \"rating\": [{\n" +
                "            \"value\": 8,\n" +
                "            \"review\": \"mike\"\n" +
                "        }]\n" +
                "    }";
        String result = JSONPostOperator.makeRequest(uri,json);
        assertNotNull(result);
    }

}