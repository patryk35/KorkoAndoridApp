package com.example.patryk.korko.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.patryk.korko.R;
import com.example.patryk.korko.activities.searchFragments.ListFragment;
import com.example.patryk.korko.activities.searchFragments.Map;
import com.example.patryk.korko.activities.searchFragments.SearchFragment;
import com.example.patryk.korko.operators.OpinionOperator;
import com.example.patryk.korko.patterns.MapAndLocalizationSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;

public class SearchActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static String currentEmail;
    private static int counter;
    private static boolean isShowed;
    private static View currentView;
    private OpinionOperator opinionOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Location location = MapAndLocalizationSingleton.getInstance().getLocation();
        if(location == null){
            SharedPreferences sp = getSharedPreferences("pl.korko.Kqrko", Context.MODE_PRIVATE);
            try{
                location.setLongitude(Double.parseDouble(sp.getString("lng", null)));
                location.setLatitude(Double.parseDouble( sp.getString("lat", null)));
            } catch (Exception e){
            }

        }
        setContentView(R.layout.activity_search);
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }
        ViewsSingleton.getInstance().setSearchView(findViewById(R.id.container));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    public void addRating(View view){
        Intent intent = new Intent(".AddRating");
        startActivity(intent);
    }
    public void showOpinons(View view){
        if(!isShowed){
            counter=0;
            opinionOperator = new OpinionOperator(SearchActivity.currentEmail,currentView);
            opinionOperator.getNextOpinion(0);
            ((Button)currentView.findViewById(R.id.buttonShowOpinion)).setText("Schowaj opinie");
            currentView.findViewById(R.id.ratingBar4).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.imageViewPersonPhoto).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.textViewOpinion).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.opinionLayout).setVisibility(View.VISIBLE);
            boolean checker = false;


            isShowed=true;
        }else{
            ((Button)currentView.findViewById(R.id.buttonShowOpinion)).setText("Zobacz opinie");
            currentView.findViewById(R.id.ratingBar4).setVisibility(View.GONE);
            currentView.findViewById(R.id.imageViewPersonPhoto).setVisibility(View.GONE);
            currentView.findViewById(R.id.textViewOpinion).setVisibility(View.GONE);
            currentView.findViewById(R.id.buttonNextOpinion).setVisibility(View.GONE);
            currentView.findViewById(R.id.buttonPreviousOpinion).setVisibility(View.GONE);
            currentView.findViewById(R.id.opinionLayout).setVisibility(View.GONE);
            //(new BottomScheetButtonsViewOperator()).setButtonsLook(view);
            isShowed=false;
        }

    }
    public void checkPriceStatus(View view) {
        boolean isPriceUsed = true;
        if (((CheckBox) findViewById(R.id.levelStudyCheckBox)).isChecked()) {
            isPriceUsed = true;
        } else if (((CheckBox) findViewById(R.id.levelMiddleScholCheckBox)).isChecked()) {
            isPriceUsed = true;
        } else if (((CheckBox) findViewById(R.id.levelHighSchoolCheckBox)).isChecked()) {
            isPriceUsed = true;
        } else isPriceUsed = ((CheckBox) findViewById(R.id.levelElementaryCheckBox)).isChecked();
        if (isPriceUsed) {
            findViewById(R.id.priceSeekBar).setVisibility(View.VISIBLE);
            findViewById(R.id.priceTextView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.priceSeekBar).setVisibility(View.INVISIBLE);
            findViewById(R.id.priceTextView).setVisibility(View.INVISIBLE);
        }
    }
    public void nextOpinion(View view) {
        counter++;
        opinionOperator.getNextOpinion(counter);
    }
    public void previousOpinion(View view) {
        counter--;
        opinionOperator.getNextOpinion(counter);
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    SearchFragment searchFragment = new SearchFragment();
                    return searchFragment;
                case 1:
                    Map map = new Map();
                    return map;
                case 2:
                     ListFragment list= new ListFragment();
                    return list;
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Wyszukaj";
                case 1:
                    return "Mapa";
                case 2:
                    return "Lista";
            }
            return null;
        }
    }

    public static void setCurrentEmail(String currentEmail) {
        SearchActivity.currentEmail = currentEmail;
    }

    public static void setCurrentView(View currentView) {
        SearchActivity.currentView = currentView;
    }

    public static void setIsShowed(boolean isShowed) {
        SearchActivity.isShowed = isShowed;
    }
}
