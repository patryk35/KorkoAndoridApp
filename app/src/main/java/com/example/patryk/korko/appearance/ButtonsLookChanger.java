package com.example.patryk.korko.appearance;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;

/**
 * Created by Patryk on 2017-05-23.
 */

public class ButtonsLookChanger {

    public static void buttonAddProperties(Button button, int height) {
        GradientDrawable gradientDrawablePressed = generateGradientPressed();
        GradientDrawable gradientDrawableReleased = generateGradientReleased();
        if(height != -1){
            button.setHeight(height);
        }
        button.setBackground(gradientDrawableReleased);
        button.setOnTouchListener(new ButtonOnTouchListener(gradientDrawablePressed, gradientDrawableReleased));
    }
    private static GradientDrawable generateGradientPressed() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.argb(40, 68, 129, 205));
        gradientDrawable.setStroke(10, Color.argb(100, 68, 129, 195));
        gradientDrawable.setGradientRadius(30f);
        gradientDrawable.setCornerRadius(25f);
        return gradientDrawable;
    }

    private static GradientDrawable generateGradientReleased() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.argb(255, 68, 129, 205));
        gradientDrawable.setGradientRadius(30f);
        gradientDrawable.setCornerRadius(25f);
        return gradientDrawable;
    }

}
