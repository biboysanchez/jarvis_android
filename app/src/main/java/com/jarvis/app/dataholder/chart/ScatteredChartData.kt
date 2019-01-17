package com.jarvis.app.dataholder.chart

import android.graphics.Color
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.jarvis.app.R.id.lineChartEquities
import com.jarvis.app.helpers.ValueFormatter


import java.util.ArrayList

class ScatteredChartData() {
    fun initChart(chart:ScatterChart){
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setTouchEnabled(true)
        chart.maxHighlightDistance = 50f
        chart.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setMaxVisibleValueCount(200)
        chart.setExtraOffsets(0f,10f,0f,20f)
        chart.setPinchZoom(true)

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.setDrawInside(false)
        l.xOffset = 5f

        val yl = chart.axisLeft
        yl.axisMinimum = 0f

        chart.axisRight.isEnabled = false
        val xl = chart.xAxis
        xl.setDrawGridLines(false)
        setData(chart, 20, 100)
    }

    private fun setData(chart: ScatterChart, progressX: Int, progressY: Int){
        val values1 = ArrayList<Entry>()
        val values2 = ArrayList<Entry>()

        for (i in 0 until progressX) {
            val `val` = (Math.random() * progressY).toFloat() + 3
            values1.add(Entry(i.toFloat(), `val`))
        }

        for (i in 0 until progressX) {
            val `val` = (Math.random() * progressY).toFloat() + 3
            values2.add(Entry(i + 0.33f, `val`))
        }

        val set1 = ScatterDataSet(values1, "Currently In SIL Portfolio")
        set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        set1.color = Color.parseColor("#1A998E")

        val set2 = ScatterDataSet(values2, "Currently In SIL Portfolio")
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        set2.color = Color.parseColor("#9E9E9E")
        set1.scatterShapeSize = 18f
        set2.scatterShapeSize = 18f

        val dataSets = ArrayList<IScatterDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)

        val arrTitle = ArrayList<String>()
        arrTitle.add("AAA")
        arrTitle.add("AA+")
        arrTitle.add("AA")
        arrTitle.add("AA+")
        arrTitle.add("A+")
        arrTitle.add("A-")
        arrTitle.add("AA")
        arrTitle.add("BB+")
        arrTitle.add("BB")
        arrTitle.add("AA-")
        arrTitle.add("B+")
        arrTitle.add("B-")
        arrTitle.add("A+")
        arrTitle.add("CC+")
        arrTitle.add("CC")
        arrTitle.add("A-")
        arrTitle.add("C+")
        arrTitle.add("C-")
        arrTitle.add("BBB+")
        arrTitle.add("DD+")
        arrTitle.add("DD")
        arrTitle.add("DD-")
        arrTitle.add("D+")
        arrTitle.add("D-")

        val xAxis = chart.xAxis
        xAxis?.valueFormatter = ValueFormatter(arrTitle)

        for (iSet in dataSets) {
            val set = iSet as ScatterDataSet
            set.setDrawValues(false)
        }

        // create a data object with the data sets
        val data = ScatterData(dataSets)
        chart.data = data
        chart.invalidate()
    }
}
