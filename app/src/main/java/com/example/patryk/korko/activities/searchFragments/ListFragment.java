package com.example.patryk.korko.activities.searchFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patryk.korko.R;
import com.example.patryk.korko.dummy.DummyContent.DummyItem;
import com.example.patryk.korko.patterns.FacadeSingleton;
import com.example.patryk.korko.patterns.ViewsSingleton;

public class ListFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_main, container, false);
        BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout2));
        ViewsSingleton.getInstance().setListView(view);
        ViewsSingleton.getInstance().setListListener(mListener);
        FacadeSingleton.getInstance().getFacade().checkSearch();
        FacadeSingleton.getInstance().getFacade().searchRecords();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
