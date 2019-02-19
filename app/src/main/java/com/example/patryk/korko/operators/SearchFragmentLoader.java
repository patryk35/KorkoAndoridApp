package com.example.patryk.korko.operators;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.patryk.korko.R;
import com.example.patryk.korko.patterns.ViewsSingleton;

/**
 * Created by Patryk on 2017-04-20.
 */

public class SearchFragmentLoader {
    public void setPriceSeekBar() {
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        final TextView textView = (TextView) view.findViewById(R.id.priceTextView);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.priceSeekBar);
        textView.setText("Cena:");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Cena do: " + seekBar.getProgress() * 10 + " złoty");
            }
        });
    }

    public void setAreaSeekBar() {
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        final TextView textView = (TextView) view.findViewById(R.id.areaTextView);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.areaSeekBar);
        textView.setText("Odległość:");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Odległość do: " + seekBar.getProgress() + " kilometrów");
            }
        });
    }
}

