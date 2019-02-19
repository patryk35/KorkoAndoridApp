package com.example.patryk.korko.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.operators.BottomSheetOperator;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.tools.FacebookPhotoDownloader;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.example.patryk.korko.appearance.ButtonsLookChanger.buttonAddProperties;

public class AddRating extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String oauth;
    private String userName;
    private AccessToken accessToken;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoginManager.getInstance().logOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setVisibility(View.INVISIBLE);
        callbackManager = CallbackManager.Factory.create();
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        image = (ImageView) findViewById(R.id.imageViewProfilPhoto);
        image.setVisibility(View.INVISIBLE);

        userName = null;
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("Zalogowano pomyślnie!");
                oauth = loginResult.getAccessToken().getUserId();
                accessToken = loginResult.getAccessToken();
                Log.d("log", "User ID: "
                        + loginResult.getAccessToken().getUserId());
                image.setVisibility(View.VISIBLE);
                setUpForm();
            }

            @Override
            public void onCancel() {
                info.setText("Przerwano logowanie!");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Wystąpił błąd logowania!");

            }
        });
    }

    private void setUpForm() {
        new FacebookPhotoDownloader(image).execute(oauth);
        getFacebookJSONData();
    }

    private void setVisibility(int isVisible) {
        findViewById(R.id.adderName).setVisibility(isVisible);
        findViewById(R.id.textView4).setVisibility(isVisible);
        findViewById(R.id.ratingBar2).setVisibility(isVisible);
        findViewById(R.id.editTextAddOpinion).setVisibility(isVisible);
        findViewById(R.id.imageViewProfilPhoto).setVisibility(isVisible);
        findViewById(R.id.buttonSendOpinion).setVisibility(isVisible);
        buttonAddProperties((Button) findViewById(R.id.buttonSendOpinion), -1);
    }

    private void getFacebookJSONData() {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            userName = object.getString("name");
                            ((TextView) findViewById(R.id.adderName)).setText(userName);
                            setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Błąd pobierania danych!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private boolean status = false;

    public void sendOpinion(View view) {
        String review = ((EditText) findViewById(R.id.editTextAddOpinion)).getText().toString();
        String[] split = review.split("\\s+");
        Float ratingValue = ((RatingBar) findViewById(R.id.ratingBar2)).getRating();
        StringBuilder sb = new StringBuilder();
        sb.append("http://korko.cf:3000/api/v1/coach/addRating?oauth=");
        sb.append(BottomSheetOperator.currentOauth);
        sb.append("&revOAuth=").append(oauth);
        sb.append("&value=").append(ratingValue);
        sb.append("&review=");
        for (int i = 0; i < split.length; i++) {
            try {
                sb.append(URLEncoder.encode(split[i], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e("Error", e.getMessage());
            }
            if ((i + 1) != split.length)
                sb.append("+");
        }
        final String url = sb.toString();
        final Context context = view.getContext();
        Log.i("url", url);
        Thread send = new Thread() {
            @Override
            public void run() {
                FacadeSingleton.getInstance().getFacade().httpMakeCall(url);
                status = true;
            }
        };
        send.start();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog((AddRating.this));
        progressDialog.setTitle("Wysyłanie");
        progressDialog.setMessage("Czekaj ...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        Thread dialog = new Thread() {
            @Override
            public void run() {
                while (!status) ;
                progressDialog.dismiss();
                LoginManager.getInstance().logOut();
            }
        };
        dialog.start();
        info.setText("Wysłano pomyślnie!");
        Thread wait = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("sendOpinion", "InterruptedException");
                } finally {
                    finish();
                }
            }
        };
        wait.start();
    }



}