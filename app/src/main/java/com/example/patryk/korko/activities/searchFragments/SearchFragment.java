package com.example.patryk.korko.activities.searchFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patryk.korko.R;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;

public class SearchFragment extends Fragment {



    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ViewsSingleton.getInstance().setSearchFragmentView(view);
        FacadeSingleton.getInstance().getFacade().setUpSearchView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
