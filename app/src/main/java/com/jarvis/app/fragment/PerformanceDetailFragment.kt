package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.jarvis.app.R
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.PerformanceDetail
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_performance_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class PerformanceDetailFragment: BaseFragment() {
    private lateinit var mView:View
    private var arrLiquidProfile: ArrayList<ArrayList<PerformanceDetail>>? = ArrayList()
    private var arrLiquidTitle:ArrayList<String>? = ArrayList()

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
        getLiquidityProfile()
    }

    private fun getLiquidityProfile(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.liquidityProfile, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        val arr = JSONObject(response).obj("message_data").arr("chart_data_list")
                        arrLiquidProfile = ArrayList()
                        arrLiquidTitle = ArrayList()
                        for (i in 0 until arr.length()){
                            val list:ArrayList<PerformanceDetail> = ArrayList()
                            val jsonArray = arr.getJSONArray(i)
                            for (z in 0 until jsonArray.length()){
                                val arrDetail = PerformanceDetail(jsonArray.getJSONObject(z))
                                list.add(arrDetail)
                                if (!arrLiquidTitle!!.contains(arrDetail.target.trim())) {
                                    arrLiquidTitle?.add(arrDetail.target.trim())
                                }
                            }
                            arrLiquidProfile?.add(list)
                        }

                        setStackBarChart()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setStackBarChart(){
        barChartLiquidity?.setPinchZoom(false)
        barChartLiquidity?.setDrawValueAboveBar(false)
        barChartLiquidity?.isHighlightFullBarEnabled = false
        barChartLiquidity?.setExtraOffsets(0f,0f,0f,20f)
        barChartLiquidity?.xAxis?.setDrawGridLines(false)
        barChartLiquidity?.xAxis?.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis = barChartLiquidity?.axisLeft
        leftAxis?.axisMinimum = 0f
        barChartLiquidity?.axisRight?.isEnabled = false

        val xLabels = barChartLiquidity?.xAxis
        xLabels?.position = XAxis.XAxisPosition.BOTTOM
        xLabels?.setLabelCount(arrLiquidTitle!!.size, true)

        val l = barChartLiquidity?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l?.setDrawInside(false)
        l?.formSize = 8f
        l?.formToTextSpace = 4f
        l?.xEntrySpace = 6f

        val values = ArrayList<BarEntry>()
        for (i in 0 until arrLiquidProfile!!.size){
            val arr = arrLiquidProfile?.get(i)
            arr?.sortWith(Comparator { o1, o2 -> o2?.saham!!.compareTo(o1!!.saham) })
            for (z in 0 until arr!!.size){
                val performanceDetail = arr[z]
                values.add(BarEntry(i.toFloat(), performanceDetail.saham.toFloat()))
            }
        }

        val xAxis = barChartLiquidity?.xAxis
        xAxis?.valueFormatter = ValueFormatter(arrLiquidTitle)
        xAxis?.granularity = 0.5f
        val set: BarDataSet
        if (barChartLiquidity?.data != null && barChartLiquidity?.data?.dataSetCount!! > 0) {
            set = barChartLiquidity.data.getDataSetByIndex(0) as BarDataSet
            set.values = values
            set.setColors(Color.parseColor("#F3B62C"), Color.parseColor("#21C6B7"))
            barChartLiquidity.data.notifyDataChanged()
            barChartLiquidity.notifyDataSetChanged()
        } else {
            set = BarDataSet(values, "Statistics Vienna 2014")
            set.setDrawIcons(false)
            set.setColors(Color.parseColor("#F3B62C"), Color.parseColor("#21C6B7"))
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)
            val data = BarData(dataSets)
            data.setValueFormatter(StackedValueFormatter(false, "", 1))
            data.setValueTextColor(Color.WHITE)
            barChartLiquidity?.data = data
        }

        val sets = barChartLiquidity?.data?.dataSets
        if (sets != null) {
            for (iSet in sets) {
                val set = iSet as BarDataSet
                set.setDrawValues(!set.isDrawValuesEnabled)
            }
        }

        barChartLiquidity?.description = null
        barChartLiquidity?.legend?.isEnabled = true
        barChartLiquidity?.setDrawValueAboveBar(true)
        barChartLiquidity?.setFitBars(false)
        barChartLiquidity?.invalidate()
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

        val values = ArrayList<BarEntry>()

        for (i in 0 until 10) {
            val mul = 11.toFloat()
            val val1 = (Math.random() * mul).toFloat() + mul / 2
            val val2 = (Math.random() * mul).toFloat() + mul / 2
            values.add(BarEntry(i.toFloat(), floatArrayOf(val1, val2)))
        }

        val set1: BarDataSet
        if (barChartLiquidity?.data != null && barChartLiquidity?.data?.dataSetCount!! > 0) {
            set1 = barChartLiquidity.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            set1.setColors(Color.parseColor("#21C6B7"), Color.parseColor("#F3B62C"))
            barChartLiquidity.data.notifyDataChanged()
            barChartLiquidity.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Statistics Vienna 2014")
            set1.setDrawIcons(false)
            set1.setColors(Color.parseColor("#21C6B7"), Color.parseColor("#F3B62C"))
            set1.stackLabels = arrayOf("Births", "Divorces")

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueFormatter(StackedValueFormatter(false, "", 1))
            data.setValueTextColor(Color.WHITE)
            barChartLiquidity?.data = data
        }

        val sets = barChartLiquidity?.data?.dataSets
        if (sets != null) {
            for (iSet in sets) {
                val set = iSet as BarDataSet
                set.setDrawValues(!set.isDrawValuesEnabled)
            }
        }

        barChartLiquidity?.description = null
        barChartLiquidity?.legend?.isEnabled = false
        barChartLiquidity?.setDrawValueAboveBar(false)
        barChartLiquidity?.setFitBars(true)
        barChartLiquidity?.invalidate()
    }
}