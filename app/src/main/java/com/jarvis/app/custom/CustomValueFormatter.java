package com.jarvis.app.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.jarvis.app.helpers.ValueFormatter;
import java.text.DecimalFormat;

public class CustomValueFormatter extends ValueFormatter {
    private DecimalFormat mFormat;
    private String suffix;

    public CustomValueFormatter(String suffix) {
        this.mFormat = new DecimalFormat("###,###,###,##");
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + suffix;
    }

}
