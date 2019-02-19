package com.example.patryk.korko.operators;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.patryk.korko.dummy.DummyContent;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.patryk.korko.tools.SubjectsNamesOperator.parseSubjectsToPolish;


public class JSONDateBaseOperator {
    private List<DummyContent.DummyItem> list;
    private static LatLng[] positionArray;
    private String url;

    public void searchInDateBase(String url) {
        list = new ArrayList<DummyContent.DummyItem>();
        this.url = url;
        startBase();
    }

    private void startBase() {
        new GetCouches().execute();
    }

    private class GetCouches extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String jsonStr = FacadeSingleton.getInstance().getFacade().httpMakeCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray coaches = jsonObj.getJSONArray("coaches");
                    positionArray = new LatLng[coaches.length()];
                    for (int i = 0; i < coaches.length(); i++) {
                        JSONObject c = coaches.getJSONObject(i);
                        JSONObject profile = c.getJSONObject("profile");
                        if (profile == null)
                            break;
                        JSONObject loc = c.getJSONObject("loc");
                        JSONObject geometry = loc.getJSONObject("geometry");
                        JSONObject description = c.getJSONObject("description");
                        JSONArray subjects = description.getJSONArray("subjects");

                        String name = profile.getString("name");
                        String surname = profile.getString("surname");
                        String id = (i+1) + "";
                        String email = profile.getString("email");
                        String oauth = profile.getString("oauth");

                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < subjects.length(); j++) {
                            JSONObject currentPosition = subjects.getJSONObject(j);
                            sb.append(parseSubjectsToPolish(currentPosition.getString("name")));
                            if ((j + 1) < subjects.length()) {
                                sb.append(", ");
                            }
                        }
                        if(sb == null){
                            sb.append("Brak cen");
                        }

                        positionArray[i] = new LatLng(geometry.getJSONArray("coordinates").getDouble(1), geometry.getJSONArray("coordinates").getDouble(0));

                        DummyContent.DummyItem item;
                        final Map<String, DummyContent.DummyItem> ITEM_MAP = new HashMap<String, DummyContent.DummyItem>();
                        item = new DummyContent.DummyItem(id, name + " " + surname, email, sb.toString(), oauth, ((int)getDistance(positionArray[i])/1000));
                        list.add(item);
                        ITEM_MAP.put(id, item);


                    }
                } catch (final JSONException e) {
                    Log.e("DBO.doItBackground()", "JSON loading error");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            FacadeSingleton.getInstance().getFacade().updateRefreshData(positionArray, list);
            if (positionArray != null) {
                FacadeSingleton.getInstance().getFacade().updateMap();
                FacadeSingleton.getInstance().getFacade().updateList();
            } else {
                Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Brak połączenia z bazą danych!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static float getDistance(LatLng position){
        float[] results = new float[1];
        Location.distanceBetween(MapAndLocalizationSingleton.getInstance().getLocation().getLatitude(),
                MapAndLocalizationSingleton.getInstance().getLocation().getLongitude(),
                position.latitude, position.longitude, results);
        return results[0];
    }
}
