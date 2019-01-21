package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jarvis.app.R
import com.jarvis.app.custom.MyMarkerView
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_currency_research.*

class CurrencyResearchFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "SummaryExposureFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_research, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBlankTitle?.text = mActivity?.viewModel!!.title
        setEventListener()
        setLineChart()

        radioGroupTime?.check(R.id.rad12M)
    }

    private fun setEventListener(){
        tvCalculate?.setOnClickListener {
            etUsdRates?.setText("1.25")
            etIdrRates?.setText("6.50")
            etSpotRate?.setText("14.50")
            etFroward?.setText("17.50")
        }

        tvResetSimulation?.setOnClickListener {
            etUsdRates?.setText("")
            etIdrRates?.setText("")
            etSpotRate?.setText("")
            etFroward?.setText("")
        }
    }

    private fun setLineChart(){
        val entries:ArrayList<Entry>? = ArrayList()
        entries?.add(Entry(0f, 15000.00f))
        entries?.add(Entry(1f, 15050.00f))
        entries?.add(Entry(2f, 15150.00f))
        entries?.add(Entry(3f, 15300.00f))
        entries?.add(Entry(4f, 15450.00f))
        entries?.add(Entry(5f, 15500.00f))

        val labels:ArrayList<String> = ArrayList()
        labels.add("M0")
        labels.add("M1")
        labels.add("M3")
        labels.add("M6")
        labels.add("M9")
        labels.add("M12")
        val dataSet = LineDataSet(entries, "Label")
        dataSet.color = Color.parseColor("#21C6B7")
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 5f
        dataSet.setCircleColor(Color.parseColor("#21C6B7"))
        dataSet.valueTextColor = android.R.color.transparent

        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = lineChartCurrency!!
        lineChartCurrency?.marker = mv

        lineChartCurrency?.legend?.isEnabled = false
        lineChartCurrency?.description = null
        lineChartCurrency?.axisRight?.setDrawLabels(false)
        lineChartCurrency?.axisLeft?.setDrawLabels(false)
        lineChartCurrency?.xAxis?.setDrawGridLines(false)
        lineChartCurrency?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        lineChartCurrency?.animateX(1600)

        lineChartCurrency?.xAxis?.setLabelCount(labels.size, true)
        lineChartCurrency?.xAxis?.setValueFormatter { value, axis ->
            return@setValueFormatter labels[value.toInt()]
        }

        val data =  LineData(dataSet)
        lineChartCurrency?.data = data
        lineChartCurrency?.invalidate()
    }
}