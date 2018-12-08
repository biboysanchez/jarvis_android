package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.jarvis.app.R
import kotlinx.android.synthetic.main.fragment_performance_detail.*
import java.util.ArrayList

class PerformanceDetailFragment: BaseFragment() {
    private lateinit var mView:View

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "PerformanceDetailFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_performance_detail, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnStackBarChart()
    }

    private fun returnStackBarChart(){
        // drawn
        barChartLiquidity?.setMaxVisibleValueCount(40)
        // scaling can now only be done on x- and y-axis separately
        barChartLiquidity?.setPinchZoom(false)
        barChartLiquidity?.setDrawGridBackground(false)
        barChartLiquidity?.setDrawBarShadow(false)

        barChartLiquidity?.setDrawValueAboveBar(false)
        barChartLiquidity?.isHighlightFullBarEnabled = false

        // change the position of the y-labels
        val leftAxis = barChartLiquidity?.axisLeft
      //  leftAxis?.valueFormatter = MyValueFormatter("K")
        leftAxis?.axisMinimum = 0f // this replaces setStartAtZero(true)
        barChartLiquidity?.axisRight?.isEnabled = false

        val xLabels = barChartLiquidity?.xAxis
        xLabels?.position = XAxis.XAxisPosition.TOP

        //chart.setDrawXLabels(false)
        //chart.setDrawYLabels(false)

        val l = barChartLiquidity?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l?.orientation = Legend.LegendOrientation.HORIZONTAL
        l?.setDrawInside(false)
        l?.formSize = 8f
        l?.formToTextSpace = 4f
        l?.xEntrySpace = 6f

//        val values = ArrayList<BarEntry>()
//
//        for (i in 0 until 40) {
//            val mul = 41.toFloat()
//            val val1 = (Math.random() * mul).toFloat() + mul / 2
//            val val2 = (Math.random() * mul).toFloat() + mul / 2
//            values.add(BarEntry(i.toFloat(), floatArrayOf(val1, val2)))
//        }

        val groupCount = 4
        val startYear = 1980
        val endYear = startYear + groupCount

        val values1 = ArrayList<BarEntry>()
        val randomMultiplier = 50f
        for (i in startYear until endYear) {
            values1.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
            values1.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
        }

        val set1: BarDataSet
        if (barChartLiquidity?.data != null && barChartLiquidity?.data?.dataSetCount!! > 0) {
            set1 = barChartLiquidity.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values1
            barChartLiquidity.data.notifyDataChanged()
            barChartLiquidity.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values1, "Statistics Vienna 2014")
            set1.setDrawIcons(false)
            set1.setColors()
            set1.stackLabels = arrayOf("Births", "Divorces")

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueFormatter(StackedValueFormatter(false, "", 1))
            data.setValueTextColor(Color.WHITE)
            barChartLiquidity?.data = data
        }

        barChartLiquidity?.setFitBars(true)
        barChartLiquidity?.invalidate()
    }
}