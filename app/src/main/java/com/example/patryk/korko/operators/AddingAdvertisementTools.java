package com.example.patryk.korko.operators;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.korko.R;
import com.example.patryk.korko.containers.JSONAddingUserContainer;
import com.example.patryk.korko.patterns.ViewsSingleton;

/**
 * Created by Patryk on 2017-05-23.
 */

public class AddingAdvertisementTools {
    public static void addSubjectToList(JSONAddingUserContainer container, View view) {
        String name = checkValuesOfSubject((Spinner) view.findViewById(R.id.spinnerSubject));
        if (name != null) {
            if (!container.checkIsItSetted(name)) {
                StringBuilder twText = new StringBuilder();
                int counter = 0;
                twText.append(name).append(": ");
                String primaryPrice = checkValuesOfSubject((Spinner) view.findViewById(R.id.spinnerLevel1));
                if (primaryPrice != null) {
                    twText.append("S. Podst. ");
                    counter++;
                }
                String secondaryPrice = checkValuesOfSubject((Spinner) view.findViewById(R.id.spinnerLevel2));
                if (secondaryPrice != null) {
                    twText.append("Gimnazjum ");
                    counter++;
                }
                String highschoolPrice = checkValuesOfSubject((Spinner) view.findViewById(R.id.spinnerLevel3));
                if (highschoolPrice != null) {
                    twText.append("S. Średnia ");
                    counter++;
                }
                String uniPrice = checkValuesOfSubject((Spinner) view.findViewById(R.id.spinnerLevel4));
                if (uniPrice != null) {
                    twText.append("Studia ");
                    counter++;
                }
                if (counter > 0) {
                    container.addSubject(name, primaryPrice, secondaryPrice, highschoolPrice, uniPrice, counter);
                    TextView tw = (TextView) view.findViewById(R.id.textViewAdded);
                    tw.setText(tw.getText() + "\n" + twText.toString());
                } else {
                    Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Musisz wybrać co najmniej jedną cenę", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Ten przedmiot został już wcześniej dodany", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ViewsSingleton.getInstance().getAppContext(), "Musisz wybrać nazwę przedmiotu", Toast.LENGTH_SHORT).show();
        }
    }

    private static String checkValuesOfSubject(Spinner spinner) {
        String result = (String) spinner.getSelectedItem();
        if (!result.equals("Przedmiot") && !result.equals("Cena") && !result.equals("Brak")) {
            return result;
        }
        return null;
    }
}
