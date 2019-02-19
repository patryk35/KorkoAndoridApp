package com.example.patryk.korko.appearance;

import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Patryk on 2017-05-15.
 */

public class ButtonOnTouchListener implements View.OnTouchListener {

    private GradientDrawable gradientDrawablePressed;
    private GradientDrawable gradientDrawableReleased;

    public ButtonOnTouchListener(GradientDrawable gradientDrawablePressed, GradientDrawable gradientDrawableReleased) {
        this.gradientDrawablePressed = gradientDrawablePressed;
        this.gradientDrawableReleased = gradientDrawableReleased;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackground(gradientDrawablePressed);
                break;
            case MotionEvent.ACTION_UP:
                v.setBackground(gradientDrawableReleased);
                v.callOnClick();
                break;
        }
        return true;
    }

}