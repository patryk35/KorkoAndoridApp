package com.example.patryk.korko.operators;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    public String makeCall(String stringUrl) {
        String response = null;
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            Log.e("HttpHandler.makeCall", "MalformedURLException! Try later or check your connection!");
        } catch (ProtocolException e) {
            Log.e("HttpHandler.makeCall", "ProtocolException! Try later or check your connection!");
        } catch (Exception e) {
            Log.e("HttpHandler.makeCall", "Http Error! Try later or check your connection!");
        }
        return response;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null)
                sb.append(line).append('\n');
        } catch (IOException e) {
            Log.e("convertStreamToString", "IOException");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("convertStreamToString", "IOException");
            }
        }
        return sb.toString();
    }
}



