package com.example.patryk.korko.operators;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.patryk.korko.R;
import com.example.patryk.korko.containers.OpinionContainer;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.tools.FacebookPhotoDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Patryk on 2017-05-10.
 */

public class OpinionOperator {
    View view;
    private Context appContext;
    private BottomSheetBehavior bottomSheetBehavior = null;
    private int currentOpinion;
    private String url, urlAuth;

    public OpinionOperator(String email, View view) {
        url = "https://korko.cf:3001/api/v1/coach/searchCategories?email=" + email;
        this.view = view;
        appContext = view.getContext();
        urlAuth = view.getResources().getString(R.string.facebook_app_id);
    }

    public void getNextOpinion(int number) {
        currentOpinion = number;
        new GetOption().execute(url);
    }

    private class GetOption extends AsyncTask<String, Void, OpinionContainer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected OpinionContainer doInBackground(String... url) {
            String jsonStr = FacadeSingleton.getInstance().getFacade().httpMakeCall(url[0]);
            StringBuilder fbURL = new StringBuilder().append("https://graph.facebook.com/");
            OpinionContainer opinionContainer = new OpinionContainer();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray opinions = jsonObj.getJSONArray("coaches");
                    JSONObject opinion = opinions.getJSONObject(0);
                    JSONArray ratings = opinion.getJSONArray("rating");
                    int lengthOfRecords = ratings.length();
                    if (ratings.getJSONObject(currentOpinion) != null) {
                        JSONObject currentObject = ratings.getJSONObject(currentOpinion);
                        opinionContainer.setRating(currentObject.getDouble("value"));
                        opinionContainer.setOpinion(currentObject.getString("review"));
                        opinionContainer.setImgURL(currentObject.getString("oauth"));
                        fbURL.append(currentObject.getString("oauth")).append("?access_token=").append(urlAuth);
                    }
                    if (currentOpinion < lengthOfRecords - 1) {
                        opinionContainer.setNextOpinion(true);
                    } else {
                        opinionContainer.setNextOpinion(false);
                    }
                } catch (final JSONException e) {
                    Log.e("OpinionOperatorJSON", "Error during loading JSON data");
                }
            }
            return opinionContainer;
        }

        @Override
        protected void onPostExecute(OpinionContainer result) {
            super.onPostExecute(result);
            if (result != null) {
                ((TextView) view.findViewById(R.id.textViewOpinion)).setText(result.getOpinion());
                ImageView image = (ImageView) view.findViewById(R.id.imageViewPersonPhoto);
                new FacebookPhotoDownloader(image).execute(result.getImgURL());
                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar4);
                if (result.getRating() >= 0 && result.getRating() <= 10) {
                    ratingBar.setVisibility(View.VISIBLE);
                    ratingBar.setRating((float) result.getRating());
                } else {
                    ratingBar.setVisibility(View.INVISIBLE);
                }
                if (result.isNextOpinion()) {
                    view.findViewById(R.id.buttonNextOpinion).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.buttonNextOpinion).setVisibility(View.INVISIBLE);
                }
                if (currentOpinion > 0) {
                    view.findViewById(R.id.buttonPreviousOpinion).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.buttonPreviousOpinion).setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
