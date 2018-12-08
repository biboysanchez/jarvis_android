package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.jarvis.app.R
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_time_series.*
import java.util.*

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
        returnPortfolioListChart()
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

    private fun returnPortfolioListChart(){
        val entries = ArrayList<Entry>()
        entries.add(Entry(10f, 80f))
        entries.add(Entry(20f, 85f))
        entries.add(Entry(30f, 80f))
        entries.add(Entry(40f, 86f))
        entries.add(Entry(50f, 81f))
        entries.add(Entry(60f, 87f))

        val entries2 = ArrayList<Entry>()
        entries2.add(Entry(10f, 75f))
        entries2.add(Entry(20f, 74f))
        entries2.add(Entry(30f, 72f))
        entries2.add(Entry(40f, 78f))
        entries2.add(Entry(50f, 74f))
        entries2.add(Entry(60f, 78f))

        val entries3 = ArrayList<Entry>()
        entries3.add(Entry(10f, 65f))
        entries3.add(Entry(20f, 66f))
        entries3.add(Entry(30f, 62f))
        entries3.add(Entry(40f, 64f))
        entries3.add(Entry(50f, 58f))
        entries3.add(Entry(60f, 67f))

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")
        set1.lineWidth = 0f
        set1.setDrawCircles(false)
        set1.setDrawFilled(true)
        set1.valueTextColor = android.R.color.transparent
        set1.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl1)

        // create a dataset and give it a type
        val set2 = LineDataSet(entries2, "DataSet 2")
        set2.lineWidth = 0f
        set2.setDrawCircles(false)
        set2.setDrawFilled(true)
        set2.valueTextColor = android.R.color.transparent
        set2.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl2)

        // create a dataset and give it a type
        val set3 = LineDataSet(entries3, "DataSet 3")
        set3.lineWidth = 0f
        set3.setDrawCircles(false)
        set3.setDrawFilled(true)
        set3.valueTextColor = android.R.color.transparent
        set3.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl3)

        // create a data object with the data sets
        val data = LineData(set1, set2, set3)

        portfolioLineChart?.legend?.isEnabled = false
        portfolioLineChart?.description = null
        portfolioLineChart?.axisRight?.setDrawLabels(false)
        portfolioLineChart?.setDrawGridBackground(true)
        portfolioLineChart?.setGridBackgroundColor(Color.parseColor("#2C7B74"))


        // set data
        portfolioLineChart?.data = data
        portfolioLineChart?.invalidate()
    }
}