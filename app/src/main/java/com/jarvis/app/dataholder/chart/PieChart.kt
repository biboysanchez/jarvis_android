package com.jarvis.app.dataholder.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build.VERSION_CODES.P
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.jarvis.app.R
import java.util.ArrayList
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.R.id.barChartAsset
import com.jarvis.app.custom.AssetLiabilityMarkerView
import com.jarvis.app.custom.PieMarkerView
import com.jarvis.app.extension.toast
import com.jarvis.app.model.Pie
import com.jarvis.app.model.PieModel
import com.jarvis.app.utils.Util


class PieChart {
    private var context: Context? = null
    private var chart: PieChart? = null
    private var data:ArrayList<PieModel>? = ArrayList()
    private var totalAssets = 0.0

    constructor(context: Context?, chart: PieChart?, data: ArrayList<PieModel>?) {
        this.context = context
        this.chart = chart
        this.data = data
        populateData()
    }

    private fun populateData(){
        chart?.setUsePercentValues(false)
        chart?.description?.isEnabled = false
        chart?.dragDecelerationFrictionCoef = 0.95f
        chart?.isDrawHoleEnabled = true
        chart?.setHoleColor(Color.WHITE)

        chart?.setTransparentCircleColor(Color.WHITE)
        chart?.setTransparentCircleAlpha(110)

        chart?.holeRadius = 68f
        chart?.transparentCircleRadius = 61f
        chart?.setDrawCenterText(true)

        chart?.rotationAngle = 0f
        chart?.isRotationEnabled = false
        chart?.isHighlightPerTapEnabled = true
        chart!!.animateY(1400, Easing.EaseInOutQuad)

        val l = chart?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l?.orientation = Legend.LegendOrientation.VERTICAL
        l?.setDrawInside(false)
        l?.textSize = 14f
        l?.yOffset = 0f
        l?.xOffset = 20f
        chart?.setDrawEntryLabels(false)
        chart?.legend?.isEnabled = false

        chart?.setDrawMarkers(true)
        val mv = PieMarkerView(context, data, R.layout.custom_marker_pie_view)
        mv.chartView = chart
        chart?.marker = mv
       setData()
    }

    private fun setData() {
        val entries = ArrayList<PieEntry>()
        val dataSet = PieDataSet(entries, "")

        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()
        for (col:PieModel in data!!){
            totalAssets += col.portofolio
            colors.add(col.color)
            entries.add(PieEntry(col.percentage.toFloat(), col.item))
        }

    //    colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(false)
        chart?.data = data
        chart?.centerText = generateCenterSpannableText()

        // undo all highlights
        chart?.highlightValues(null)
        chart?.invalidate()
    }

    private fun generateCenterSpannableText(): SpannableString {
//val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
//        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
//        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
//        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
//        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
//        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
//        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        val title = "Total Assets"
        val price = "${Util.priceFormat(totalAssets.toFloat()).replace(".00","")} B"
        val s = SpannableString("$title\n$price")
        s.setSpan(RelativeSizeSpan(.8f), 0, title.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), title.length, s.length, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), title.length, s.length, 0)
        s.setSpan(RelativeSizeSpan(1.3f), 12, s.length, 0)
        return s
    }
}