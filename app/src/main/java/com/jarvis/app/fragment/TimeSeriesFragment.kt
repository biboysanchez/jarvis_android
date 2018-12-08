package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jarvis.app.R
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_time_series.*
import java.util.*
import com.github.mikephil.charting.components.YAxis



class TimeSeriesFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "TimeSeriesFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
        setSpinner()
        returnLineChart()
    }

    private fun setSpinner(){
        spinnerPerformance?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("JCI", "Saham", "Target") )

        spinnerSelection?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("Simas Saham Bertumbuh") )

        Util.changeTextColor(spinnerPerformance, "#9E9E9E")
        Util.changeTextColor(spinnerSelection, "#9E9E9E")
    }

    private fun setRecyclerAdapter(){
        rvPerformanceAttribute?.layoutManager = LinearLayoutManager(context)
        rvPerformanceAttribute?.adapter = HomeListAdapter(context, null)
    }

    private fun returnLineChart(){
        // if disabled, scaling can be done on x- and y-axis separately
        lineChartReturnBenchMark?.setPinchZoom(false)
        lineChartReturnBenchMark?.description = null
        lineChartReturnBenchMark?.axisLeft?.setDrawLabels(true)
        lineChartReturnBenchMark?.setDrawMarkers(true)
        lineChartReturnBenchMark?.axisRight?.setDrawLabels(false)
        lineChartReturnBenchMark?.axisLeft?.setDrawZeroLine(false)
        lineChartReturnBenchMark?.xAxis?.setDrawLabels(false)
        lineChartReturnBenchMark?.legend?.isEnabled = false
        lineChartReturnBenchMark?.resetTracking()

        lineChartReturnBenchMark?.axisLeft?.setDrawGridLines(true)
        lineChartReturnBenchMark?.xAxis?.setDrawGridLines(true)

        val progress = 10
        val dataSets = ArrayList<ILineDataSet>()

        for (z in 0..1) {
            val values = ArrayList<Entry>()
            for (i in 0 until progress) {
                val `val` = Math.random() * 14
                values.add(Entry(i.toFloat(), `val`.toFloat()))
            }

            val d = LineDataSet(values, "DataSet " + (z + 1))
            d.lineWidth = 2f

            d.isHighlightEnabled = false // allow highlighting for DataSet
            d.setDrawHighlightIndicators(false)
           // d.setHighlightColor(Color.BLACK)
           // d.setDrawFilled(true)
            d.setDrawCircles(false)
            dataSets.add(d)
        }

        // make the first DataSet dashed
        //(dataSets[0] as LineDataSet).enableDashedLine(10f, 10f, 0f)
        (dataSets[0] as LineDataSet).setColors(Color.parseColor("#BDBDBD"))
        (dataSets[1] as LineDataSet).setColors(Color.parseColor("#21C6B7"))

        val data = LineData(dataSets)
        lineChartReturnBenchMark?.data = data
        lineChartReturnBenchMark?.invalidate()
    }
}