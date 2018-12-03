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
        // entry label styling
       // chart?.setEntryLabelColor(Color.WHITE)
        chart?.setDrawEntryLabels(false)
        chart?.legend?.isEnabled = false
        // chart?.setEntryLabelTypeface(tfRegular)
        //chart?.setEntryLabelTextSize(12f)
        chart?.setOnChartValueSelectedListener(object :OnChartValueSelectedListener{
            override fun onNothingSelected() {

            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val x = e?.x
                val y = e?.y
                Log.i("TAG", "x: $x y: $y")
            }

        })

       setData(4, 4F)
    }

    private val parties = arrayOf("27%", "5%", "18%", "4%", "16%", "4%", "14%", "3%", "8%", "2%")

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(27F, parties[0]))
        entries.add(PieEntry(5F, parties[1]))
        entries.add(PieEntry(18F, parties[2]))
        entries.add(PieEntry(4F, parties[3]))
        entries.add(PieEntry(16F, parties[3]))
        entries.add(PieEntry(4F, parties[3]))
        entries.add(PieEntry(14F, parties[3]))
        entries.add(PieEntry(3F, parties[3]))
        entries.add(PieEntry(8F, parties[3]))
        entries.add(PieEntry(2F, parties[3]))

        val dataSet = PieDataSet(entries, "")
       // dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#18E4D1"))
        colors.add(Color.parseColor("#239D92"))
        colors.add(Color.parseColor("#22ACC7"))
        colors.add(Color.parseColor("#2C7A53"))
        colors.add(Color.parseColor("#239E60"))
        colors.add(Color.parseColor("#19C1E3"))
        colors.add(Color.parseColor("#19E37E"))
        colors.add(Color.parseColor("#21C6B7"))
        colors.add(Color.parseColor("#2C7B74"))
        colors.add(Color.parseColor("#22C774"))

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
        var price = "34,08 B"

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