package com.jarvis.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.jarvis.app.R
import com.jarvis.app.custom.MyMarkerView
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_duration_match.*
import java.util.*

class DurationMatchFragment : BaseFragment() {

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "DurationMatchFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_duration_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBlankTitle?.text = mActivity?.viewModel!!.title
        setBarChart()
    }

    private fun setBarChart(){
        barChartAsset?.description?.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChartAsset?.setMaxVisibleValueCount(8)

        // scaling can now only be done on x- and y-axis separately
        barChartAsset?.setPinchZoom(false)

        barChartAsset?.setDrawBarShadow(false)
        barChartAsset?.setDrawGridBackground(false)

        val xAxis = barChartAsset?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)

        barChartAsset?.axisLeft?.setDrawGridLines(false)

        // setting data
        barChartData()
        setLineChart()

        // add a nice and smooth animation
        barChartAsset?.animateY(1500)
        barChartAsset?.legend?.isEnabled = true
    }

    private fun barChartData(){
        val values = ArrayList<BarEntry>()

        for (i in 0 until 8) {
            val multi = 40F
            val `val` = (Math.random() * multi).toFloat() + multi / 3
            values.add(BarEntry(i.toFloat(), `val`))
        }

        val set1: BarDataSet

        if (barChartAsset?.data != null && barChartAsset?.data?.dataSetCount!! > 0) {
            set1 = barChartAsset?.data?.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            barChartAsset?.data!!.notifyDataChanged()
            barChartAsset?.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Data Set")
            set1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            set1.setDrawValues(false)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            barChartAsset?.data = data
            barChartAsset?.setFitBars(true)
        }

        barChartAsset?.invalidate()
    }

    private fun setLineChart(){
        //lineChartSurplus?.setOnChartValueSelectedListener(context)
        lineChartSurplus?.setDrawGridBackground(false)

        // no description text
        lineChartSurplus?.description?.isEnabled = false

        // enable touch gestures
        lineChartSurplus?.setTouchEnabled(true)

        // enable scaling and dragging
        lineChartSurplus?.isDragEnabled = true
        lineChartSurplus?.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        lineChartSurplus?.setPinchZoom(true)

        // set an alternative background color
        // chart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = lineChartSurplus // For bounds control
        lineChartSurplus?.marker = mv // Set the marker to the chart

        val xl = lineChartSurplus?.xAxis
       // xl?.setAvoidFirstLastClipping(true)
        //xl?.axisMinimum = 0f

        val leftAxis = lineChartSurplus?.axisLeft
        leftAxis?.isInverted = false
       // leftAxis?.axisMinimum = 0f
       // leftAxis?.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = lineChartSurplus?.axisRight
        //rightAxis?.axisMinimum = 0f
        //rightAxis?.isEnabled = true


        // get the legend (only possible after setting data)
        val l = lineChartSurplus?.legend

        // modify the legend ...
        l?.form = Legend.LegendForm.LINE

        // don't forget to refresh the drawing
        lineChartSurplus?.invalidate()

        // add data
        lineChartData(4, 80F)
    }

    private fun lineChartData(count:Int, range:Float){
        val entries = ArrayList<Entry>()

        for (i in 0 until count) {
            val xVal = (Math.random() * range).toFloat()
            val yVal = (Math.random() * range).toFloat()
            entries.add(Entry(xVal, yVal))
        }

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")
        set1.lineWidth = 1.5f
        set1.circleRadius = 4f

        // create a data object with the data sets
        val data = LineData(set1)

        // set data
        lineChartSurplus?.data = data
        lineChartSurplus?.invalidate()
    }
}