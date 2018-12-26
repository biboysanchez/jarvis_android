package com.jarvis.app.helpers;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class ValueFormatter implements IAxisValueFormatter {
    private ArrayList<String> mValues = new ArrayList<>();

    public ValueFormatter(ArrayList<String> values) {
        this.mValues.addAll(values);
    }

    public ValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return  mValues.get((int) value);
    }
}