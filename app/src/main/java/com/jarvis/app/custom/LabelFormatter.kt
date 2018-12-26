package com.jarvis.app.custom

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.ArrayList

class LabelFormatter(private var labels: ArrayList<String>) : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return labels[value.toInt()]
    }
}
