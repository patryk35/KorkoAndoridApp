package com.example.patryk.korko.operators;

import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import com.example.patryk.korko.R;
import com.example.patryk.korko.containers.BoxesContainer;
import com.example.patryk.korko.containers.SearchValuesContainer;
import com.example.patryk.korko.patterns.ViewsSingleton;

/**
 * Created by Patryk on 2017-04-17.
 */

public class SearchOperator {
    private static SearchValuesContainer searchValuesContainer;
    private boolean valuesUpToDate;
    private static SearchValuesContainer prevSearchValuesContainer;

    public SearchOperator() {
        searchValuesContainer = new SearchValuesContainer();
    }

    public void checkSubjectsBox() {
        BoxesContainer subjectsBoxesContainer = new BoxesContainer();
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.mathCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.mathCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.phisicsCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.phisicsCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.chemistryCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.chemistryCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.geographyCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.geographyCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.biologyCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.biologyCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.historyCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.historyCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.wosCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.wosCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.polishCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.polishCheckBox)).isChecked());
        subjectsBoxesContainer.add(((CheckBox) view.findViewById(R.id.englishCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.englishCheckBox)).isChecked());
        searchValuesContainer.setSubjectsBoxesContainer(subjectsBoxesContainer);
    }

    public void checkLevelsBox() {
        BoxesContainer levelBoxesContainer = new BoxesContainer();
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        levelBoxesContainer.add(((CheckBox) view.findViewById(R.id.levelElementaryCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.levelElementaryCheckBox)).isChecked());
        levelBoxesContainer.add(((CheckBox) view.findViewById(R.id.levelMiddleScholCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.levelMiddleScholCheckBox)).isChecked());
        levelBoxesContainer.add(((CheckBox) view.findViewById(R.id.levelHighSchoolCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.levelHighSchoolCheckBox)).isChecked());
        levelBoxesContainer.add(((CheckBox) view.findViewById(R.id.levelStudyCheckBox)).getText().toString(), ((CheckBox) view.findViewById(R.id.levelStudyCheckBox)).isChecked());
        searchValuesContainer.setLevelBoxesContainer(levelBoxesContainer);
    }

    public void checkPrice() {
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        searchValuesContainer.setPrice(((SeekBar) view.findViewById(R.id.priceSeekBar)).getProgress());
    }

    public void checkArea() {
        View view = ViewsSingleton.getInstance().getSearchFragmentView();
        searchValuesContainer.setArea(((SeekBar) view.findViewById(R.id.areaSeekBar)).getProgress());
    }

    public SearchValuesContainer getSearchValuesContainer() {
        //If application is too long onPause it could disappear, so return empty container to avoid exceptions
        if (searchValuesContainer == null)
            return new SearchValuesContainer();
        return searchValuesContainer;
    }

    public void checkValuesUpToDate() {
        if(prevSearchValuesContainer != null && !prevSearchValuesContainer.equals(searchValuesContainer)){
            valuesUpToDate = false;

        }else{
            prevSearchValuesContainer = searchValuesContainer;
            valuesUpToDate = true;
        }
    }

    public boolean isValuesUpToDate() {
        return valuesUpToDate;
    }
}
