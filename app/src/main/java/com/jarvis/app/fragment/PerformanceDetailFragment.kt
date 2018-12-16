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
        var instance:PerformanceDetailFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        mView = inflater.inflate(R.layout.fragment_performance_detail, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLiquidityProfile()
    }

    fun refreshAll(){
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

                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val list:ArrayList<PerformanceDetail> = ArrayList()
                                val jsonArray = arr.getJSONArray(i)

                                for (z in 0 until jsonArray.length()){
                                    val arrDetail = PerformanceDetail(jsonArray.getJSONObject(z))
                                    list.add(arrDetail)
                                }

                                if (list.isNotEmpty()){
                                    arrLiquidProfile?.add(list)
                                }
                            }

                            if (arrLiquidProfile!!.isNotEmpty()){
                                setStackBarChart()
                            }else{
                                barChartLiquidity?.clear()
                                barChartLiquidity?.notifyDataSetChanged()
                            }
                        }else{
                            barChartLiquidity?.invalidate()
                        }
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

        val l = barChartLiquidity?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l?.setDrawInside(false)
        l?.formSize = 8f
        l?.formToTextSpace = 4f
        l?.xEntrySpace = 6f

        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()
        arrLiquidTitle = ArrayList()
        var set1: BarDataSet? = null
        var set2: BarDataSet? = null
        var title1 = ""
        var title2 = ""

        for (i in 0 until arrLiquidProfile!!.size){
            val arr = arrLiquidProfile?.get(i)
          // arr?.sortWith(Comparator { o1, o2 -> o1?.saham!!.compareTo(o2!!.saham) })
            for (z in 0 until arr!!.size){
                val performanceDetail = arr[z]

                if (performanceDetail.portfolio == "Danamas Saham"){
                    values2.add(BarEntry(i.toFloat(), performanceDetail.saham.toFloat()))
                    title2 = performanceDetail.portfolio
                    set2 = BarDataSet(values2, title2)
                    set2.color = Color.parseColor("#21C6B7")
                    set2.setDrawValues(false)
                }else{
                    values1.add(BarEntry(i.toFloat(), performanceDetail.saham.toFloat()))
                    title1 = performanceDetail.portfolio
                    set1 = BarDataSet(values1, title1)
                    set1.color = Color.parseColor("#F3B62C")
                    set1.setDrawValues(false)
                }

                if (!arrLiquidTitle!!.contains(performanceDetail.target)){
                    arrLiquidTitle?.add(performanceDetail.target)
                }
            }
        }

        val xAxis = barChartLiquidity?.xAxis
        xAxis?.granularity = 1f
        xAxis?.valueFormatter = ValueFormatter(arrLiquidTitle)
        xLabels?.setLabelCount(arrLiquidTitle!!.size-1, false)
        val set: BarDataSet
        if (barChartLiquidity?.data != null && barChartLiquidity?.data?.dataSetCount!! > 0) {
            set = barChartLiquidity.data.getDataSetByIndex(0) as BarDataSet
            set.values = values1
           // set.setColors(Color.parseColor("#F3B62C"), Color.parseColor("#21C6B7"))
            barChartLiquidity?.data?.notifyDataChanged()
            barChartLiquidity?.notifyDataSetChanged()
        } else {
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1!!)
            dataSets.add(set2!!)
            val data = BarData(dataSets)
            barChartLiquidity?.data = data
        }

        barChartLiquidity?.animateY(1200)
        barChartLiquidity?.description = null
        barChartLiquidity?.legend?.isEnabled = true
        barChartLiquidity?.isHighlightFullBarEnabled = true
        barChartLiquidity?.setDrawValueAboveBar(true)
        barChartLiquidity?.setFitBars(true)
        barChartLiquidity?.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

}