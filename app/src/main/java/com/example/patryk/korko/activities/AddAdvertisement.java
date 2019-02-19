package com.example.patryk.korko.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.containers.JSONAddingUserContainer;
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

import java.io.IOException;

import static com.example.patryk.korko.appearance.ButtonsLookChanger.buttonAddProperties;
import static com.example.patryk.korko.operators.AddingAdvertisementTools.addSubjectToList;
import static com.example.patryk.korko.operators.JSONPostOperator.makeRequest;


public class AddAdvertisement extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String oauth;
    private String userName;
    private JSONAddingUserContainer container;
    private AccessToken accessToken;
    private Context context;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginManager.getInstance().logOut();
        setContentView(R.layout.activity_add_advertisement);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        image = (ImageView) findViewById(R.id.imageViewProfilPhoto);
        image.setVisibility(View.INVISIBLE);
        userName = null;
        context = this.getBaseContext();
        container = new JSONAddingUserContainer();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("Zalogowano pomyślnie!");
                oauth = loginResult.getAccessToken().getUserId();
                container.setOauth(loginResult.getAccessToken().getUserId());
                accessToken = loginResult.getAccessToken();
                image.setVisibility(View.VISIBLE);
                findViewById(R.id.LayoutAdd).setVisibility(View.VISIBLE);
                buttonAddProperties((Button) findViewById(R.id.buttonAddNext), -1);
                buttonAddProperties((Button) findViewById(R.id.buttonSendAdd), -1);
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

    private void getFacebookJSONData() {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            userName = object.getString("name");
                            container.setFirstName(object.getString("first_name"));
                            container.setLastName(object.getString("last_name"));
                            ((TextView) findViewById(R.id.adderName)).setText(userName);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Błąd pobierania danych!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void addAdd(View view) {
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String description = ((EditText) findViewById(R.id.editTextDescription)).getText().toString();
        if (container.getSubjectsListSize() == 0) {
            Toast.makeText(context, "Musisz dodać co najmniej jeden przedmiot", Toast.LENGTH_SHORT).show();
        } else if (description == null || description == "") {
            Toast.makeText(context, "Nieprawidłowy opis", Toast.LENGTH_SHORT).show();
        } else if (email == null || email == "") {
            Toast.makeText(context, "Nieprawidłowy email", Toast.LENGTH_SHORT).show();
        } else {
            String emailParts[] = email.split("@");
            if (emailParts.length == 2) {
                container.setDescription(description);
                container.setEmail(email);
                container.setOauth(oauth);
                try {
                    Thread send = new Thread() {
                        @Override
                        public void run() {
                            try {
                                makeRequest("http://korko.cf:3000/api/v1/coach/new", container.makeStringJSON());
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        info.setText("Ogłoszenie dodano pomyślnie!");
                                        findViewById(R.id.LayoutAdd).setVisibility(View.GONE);
                                        LoginManager.getInstance().logOut();
                                    }
                                });
                            } catch (IOException e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "Nie udało się dodać ogłoszenia! Możliwe że posiadasz już dodane ogłoszenie!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    };
                    send.start();

                } catch (Exception e) {
                    Toast.makeText(context, "Nie udało się dodać ogłoszenia!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Nieprawidłowy email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addSubject(View view) {
        addSubjectToList(container, view);
    }

}
