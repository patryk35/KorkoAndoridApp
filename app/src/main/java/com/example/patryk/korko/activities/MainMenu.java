package com.example.patryk.korko.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.patryk.korko.R;

import static com.example.patryk.korko.appearance.ButtonsLookChanger.buttonAddProperties;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        refreshButtons();
    }
    public void refreshButtons() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 20;
        int height = size.y;
        buttonAddProperties((Button) findViewById(R.id.MenuChangeCityButton), width / 2);
        buttonAddProperties((Button) findViewById(R.id.MenuWWWButton), width / 2);
        buttonAddProperties((Button) findViewById(R.id.MenuSearchButton), height - 300 - width);
        buttonAddProperties((Button) findViewById(R.id.MenuAddButton), height - 300 - width);
    }

    public void WWWOpen(View view) {
        Uri uri = Uri.parse("http://www.korko.cf/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Search(View view) {
        Intent intent = new Intent(".SearchActivity");
        startActivity(intent);
    }

    public void AddAdvertisement(View view) {
        Intent intent = new Intent(".AddAdvertisement");
        startActivity(intent);
    }

    public void ChangeCity(View view) {
        SharedPreferences sp = getSharedPreferences("pl.korko.Kqrko", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.remove("City");
        spEditor.commit();
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AppStatus", 1);
        startActivity(intent);
    }
}
