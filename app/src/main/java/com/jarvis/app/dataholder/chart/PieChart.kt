package com.jarvis.app.dataholder.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build.VERSION_CODES.P
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.jarvis.app.R
import java.util.ArrayList

class PieChart {
    private var context: Context? = null
    private var chart: PieChart? = null

    constructor(context: Context?, chart: PieChart?) {
        this.context = context
        this.chart = chart
    }

    fun data(){
        chart?.setUsePercentValues(false)
        chart?.description?.isEnabled = false
     //   chart?.setExtraOffsets(5f, 10f, 5f, 5f)

        chart?.dragDecelerationFrictionCoef = 0.95f

        //chart?.setCenterTextTypeface(tfLight)
        chart?.centerText = generateCenterSpannableText()

        chart?.isDrawHoleEnabled = true
        chart?.setHoleColor(Color.WHITE)

        chart?.setTransparentCircleColor(Color.WHITE)
        chart?.setTransparentCircleAlpha(110)

        chart?.holeRadius = 72f
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

        // entry label styling
       // chart?.setEntryLabelColor(Color.WHITE)
        chart?.setDrawEntryLabels(false)
        // chart?.setEntryLabelTypeface(tfRegular)
        //chart?.setEntryLabelTextSize(12f)

       setData(4, 4F)
    }

    val parties = arrayOf(
        "${25}% - Cash",
        "${14}% - Equity",
        "${30}% - Fixed Income",
        "${60}% - Mutual Fund"
    )

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(25F, parties[0]))
        entries.add(PieEntry(14F, parties[1]))
        entries.add(PieEntry(30F, parties[2]))
        entries.add(PieEntry(60F, parties[3]))

//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (i in 0 until count) {
//            entries.add(
//                PieEntry(
//                    (Math.random() * range + range / 5).toFloat(),
//                    parties[i % parties.size]
//                    //getResources().getDrawable(R.drawable.star)
//                )
//            )
//        }

        val dataSet = PieDataSet(entries, "")
       // dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#18E4D1"))
        colors.add(Color.parseColor("#21C6B7"))
        colors.add(Color.parseColor("#239D92"))
        colors.add(Color.parseColor("#2C7B74"))

        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(false)
        chart?.data = data

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
        var price = "Rp 34,08 B"

        val s = SpannableString("$title\n$price")
        s.setSpan(RelativeSizeSpan(.8f), 0, title.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), title.length, s.length, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), title.length, s.length, 0)
        s.setSpan(RelativeSizeSpan(1.3f), 12, s.length, 0)
        //s.setSpan(StyleSpan(Typeface.ITALIC), s.length - title.length, s.length, 0)
        //s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - title.length, s.length, 0)
        return s
    }
}