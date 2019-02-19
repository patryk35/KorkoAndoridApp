package com.example.patryk.korko.operators;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.activities.SearchActivity;
import com.example.patryk.korko.containers.BottomContainer;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;
import com.example.patryk.korko.tools.FacebookPhotoDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.patryk.korko.appearance.ButtonsLookChanger.buttonAddProperties;
import static com.example.patryk.korko.tools.SubjectsNamesOperator.parseSubjectsToPolish;

/**
 * Created by Patryk on 2017-05-08.
 */

public class BottomSheetOperator {
    private View view;
    private Context appContext;
    private BottomSheetBehavior bottomSheetBehavior = null;
    public static String currentOauth;

    public void setUpBottomSheet(String type, String email) {
        if (type.equals("map")) {
            view = ViewsSingleton.getInstance().getMapView();
            bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout));
            setButtonsLook(view);
        } else {
            view = ViewsSingleton.getInstance().getListView();
            bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout2));
            setButtonsLook(view);
        }
        appContext = view.getContext();
        String url = "http://korko.cf:3000/api/v1/coach/searchCategories?email=" + email;
        new GetCoach().execute(url);
    }

    private class GetCoach extends AsyncTask<String, Void, BottomContainer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected BottomContainer doInBackground(String... url) {
            String jsonStr = FacadeSingleton.getInstance().getFacade().httpMakeCall(url[0]);
            StringBuilder sb = new StringBuilder();
            BottomContainer bottomContainer = new BottomContainer();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray coaches = jsonObj.getJSONArray("coaches");
                    JSONObject coach = coaches.getJSONObject(0);
                    JSONObject profile = coach.getJSONObject("profile");
                    if (profile != null) {
                        JSONObject description = coach.getJSONObject("description");
                        JSONArray subjects = description.getJSONArray("subjects");

                        bottomContainer.setName(profile.getString("name") + " " + profile.getString("surname"));
                        bottomContainer.setEmail(profile.getString("email"));
                        bottomContainer.setDescription(description.getString("about"));
                        bottomContainer.setOauth(profile.getString("oauth"));
                        if (coach.isNull("avergeRating")) {
                            bottomContainer.setRating(15d);
                        } else {
                            bottomContainer.setRating(coach.getDouble("avergeRating"));
                        }
                        currentOauth = profile.getString("oauth");

                        for (int i = 0; i < subjects.length(); i++) {
                            JSONObject currentPosition = subjects.getJSONObject(i);
                            sb.append("Przedmiot: <b>").append(parseSubjectsToPolish(currentPosition.getString("name"))).append(" </b>\n");
                            if (!currentPosition.isNull("primaryPrice"))
                                sb.append("\tCena Szkoła Podstawowa:;").append(currentPosition.getString("primaryPrice")).append(" zł/h").append("\n");
                            if (!currentPosition.isNull("secondaryPrice"))
                                sb.append("\tCena Gimnazjum:;").append(currentPosition.getInt("secondaryPrice")).append(" zł/h").append("\n");
                            if (!currentPosition.isNull("highschoolPrice"))
                                sb.append("\tCena Szkoła Średnia:;").append(currentPosition.getInt("highschoolPrice")).append(" zł/h").append("\n");
                            if (!currentPosition.isNull("uniPrice"))
                                sb.append("\tCena Studia:;").append(currentPosition.getInt("uniPrice")).append(" zł/h").append("\n");
                        }
                        if (sb != null) {
                            bottomContainer.setSubjects(sb.toString());
                        } else {
                            bottomContainer.setSubjects("Brak cen");
                        }
                    }
                } catch (final JSONException e) {
                    return null;
                }

            }
            return bottomContainer;
        }

        @Override
        protected void onPostExecute(BottomContainer result) {
            super.onPostExecute(result);
            if (result != null) {
                //setButtonsLook(view);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                ((TextView) view.findViewById(R.id.bottomSheetName)).setText(result.getName());
                ((TextView) view.findViewById(R.id.bottomSheetEmail)).setText(result.getEmail());
                ((TextView) view.findViewById(R.id.bottomSheetDescription)).setText("Opis: \n" + result.getDescription());
                ((TextView) view.findViewById(R.id.bottomSheetPrices)).setText(setTableFormat(result.getSubjects(), (TextView) view.findViewById(R.id.bottomSheetPrices)));
                ImageView image = (ImageView) view.findViewById(R.id.profilPhoto);
                new FacebookPhotoDownloader(image).execute(result.getOauth());

                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
                if (result.getRating() >= 0 && result.getRating() <= 10) {
                    ratingBar.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.buttonShowOpinion).setVisibility(View.VISIBLE);
                    ratingBar.setRating((float) result.getRating());
                } else {
                    ratingBar.setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.buttonShowOpinion).setVisibility(View.GONE);
                }

                final String email = result.getEmail();
                view.findViewById(R.id.imageButtonEmail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Korepetycje - Kqrko");
                        v.getContext().startActivity(Intent.createChooser(emailIntent, "Wyślij email"));
                    }
                });

                //Opinions part
                ((Button) view.findViewById(R.id.buttonShowOpinion)).setText("Zobacz opinie");
                view.findViewById(R.id.ratingBar4).setVisibility(View.GONE);
                view.findViewById(R.id.imageViewPersonPhoto).setVisibility(View.GONE);
                view.findViewById(R.id.textViewOpinion).setVisibility(View.GONE);
                view.findViewById(R.id.buttonNextOpinion).setVisibility(View.GONE);
                view.findViewById(R.id.buttonPreviousOpinion).setVisibility(View.GONE);
                view.findViewById(R.id.opinionLayout).setVisibility(View.GONE);
                SearchActivity.setCurrentEmail(email);
                SearchActivity.setCurrentView(view);
                SearchActivity.setIsShowed(false);
            } else {
                Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Wystąpił błąd podczas ładowania danych!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String setTableFormat(String s, TextView text) {
        String lines[] = s.split("\n");
        StringBuilder print = new StringBuilder();
        String parts[] = null;
        String tmp = "";
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].equalsIgnoreCase("")) {
                parts = lines[i].split(";", 2);
                if (parts.length < 2) {
                    print.append(Html.fromHtml(lines[i]) + "\n\n");
                } else {
                    if (parts == null) {
                        parts = new String[2];
                        parts[0] = "";
                        parts[1] = "";
                    }
                    tmp = "\n";
                    Paint textPaint = text.getPaint();
                    float wslength = textPaint.measureText(" ");
                    for (int j = 0; j < 380 / Math.round(wslength); j++) {
                        tmp = tmp + " ";
                    }
                    print.append(parts[0] + tmp + parts[1] + "\n");
                }
            }
        }
        return print.toString();
    }
    public static void setButtonsLook(View view) {
            buttonAddProperties((Button) view.findViewById(R.id.buttonRateUser),-1);
            buttonAddProperties((Button) view.findViewById(R.id.buttonShowOpinion),-1);
            buttonAddProperties((Button) view.findViewById(R.id.buttonNextOpinion),-1);
            buttonAddProperties((Button) view.findViewById(R.id.buttonPreviousOpinion),-1);
    }
}
