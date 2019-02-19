package com.example.patryk.korko;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.patryk.korko.activities.searchFragments.ListFragment.OnListFragmentInteractionListener;
import com.example.patryk.korko.dummy.DummyContent.DummyItem;
import com.example.patryk.korko.patterns.FacadeSingleton;

import java.util.List;

public class MyListRecyclerViewAdapter extends RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyListRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mEmailView.setText(mValues.get(position).email);
        holder.mSubjectsView.setText(mValues.get(position).subjects);
        int distanceValue = mValues.get(position).distance;
        String distance = " Odległość: " + distanceValue + "km";
        holder.mDistance.setText(distance);
        if (distanceValue < 5) {
            holder.mDistance.setTextColor(Color.GREEN);
        } else if (distanceValue < 10) {
            holder.mDistance.setTextColor(Color.BLACK);
        } else {
            holder.mDistance.setTextColor(Color.RED);
        }
        final String currentPositionEmail = mValues.get(position).email;
        holder.mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacadeSingleton.getInstance().getFacade().showBottomSheet("list", currentPositionEmail);
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mEmailView;
        public final TextView mSubjectsView;
        public final TextView mShow;
        public final TextView mDistance;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mNameView = (TextView) view.findViewById(R.id.name);
            mEmailView = (TextView) view.findViewById(R.id.email);
            mSubjectsView = (TextView) view.findViewById(R.id.textViewSubjects);
            mShow = (TextView) view.findViewById(R.id.textViewShowMore);
            mDistance = (TextView) view.findViewById(R.id.textViewDistance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

}
