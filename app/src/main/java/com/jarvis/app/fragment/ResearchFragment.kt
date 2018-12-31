package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jarvis.app.R
import com.jarvis.app.adapter.BalanceSheetAssetAdapter
import com.jarvis.app.adapter.BalanceSheetLiabilitiesAdapter
import com.jarvis.app.adapter.IncomeStatementAdapter
import com.jarvis.app.custom.MyMarkerView
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_research.*

class ResearchFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "ResearchFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_research, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity?.isShowCompany(true)
        tvBlankTitle?.text = mActivity?.viewModel!!.title

        drawAll()
    }

    private fun drawAll(){
        setLineChart()
        setBalanceSheetAsset()
        setBalanceSheetLiabilities()
        setIncomeStatementSensitivity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.isShowCompany(false)
    }

    private fun setLineChart(){
        val entries:ArrayList<Entry>? = ArrayList()
        entries?.add(Entry(0f, 11.234f))
        entries?.add(Entry(1f, 12.234f))
        entries?.add(Entry(2f, 15.234f))
        entries?.add(Entry(3f, 16.234f))
        entries?.add(Entry(4f, 19.234f))
        entries?.add(Entry(5f, 22.234f))

        val labels:ArrayList<String> = ArrayList()
        labels.add("M2")
        labels.add("M4")
        labels.add("M6")
        labels.add("M8")
        labels.add("M10")
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

    private fun setBalanceSheetAsset(){
        rvBalanceSheetAsset?.layoutManager = LinearLayoutManager(context)
        rvBalanceSheetAsset?.adapter = BalanceSheetAssetAdapter(context)
    }

    private fun setBalanceSheetLiabilities(){
        rvBalanceLiabilities?.layoutManager = LinearLayoutManager(context)
        rvBalanceLiabilities?.adapter = BalanceSheetLiabilitiesAdapter(context)
    }

    private fun setIncomeStatementSensitivity(){
        rvIncomeStatement?.layoutManager = LinearLayoutManager(context)
        rvIncomeStatement?.adapter = IncomeStatementAdapter(context)
    }
}