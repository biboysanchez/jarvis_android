package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.EntryXComparator
import com.jarvis.app.R
import com.jarvis.app.custom.MyMarkerView
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_duration_match.*
import java.util.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jarvis.app.adapter.AssetAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.double
import com.jarvis.app.extension.obj
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Cumulative
import com.jarvis.app.model.Matching
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList


class DurationMatchFragment : BaseFragment() {
    private var arrMatch :ArrayList<Matching>?          = ArrayList()
    private var arrCumulative: ArrayList<Cumulative>?   = ArrayList()
    private var arrBarTitle:ArrayList<String>?          = ArrayList()
    private var arrCumulativeTitle:ArrayList<String>?   = ArrayList()
    private var arrAssetKeys:ArrayList<ValueKey>?       = ArrayList()
    private var arrLiabilitesKeys:ArrayList<ValueKey>?  = ArrayList()
    private var mTotal = 0f

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "DurationMatchFragment"
        var instance:DurationMatchFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_duration_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBlankTitle?.text = mActivity?.viewModel!!.title

        refreshAll()
    }

    fun refreshAll(){
        setAssetLiabilityMatching()
        setCumulativeSurplus()
        setAssetList()
    }

    private fun setAssetLiabilityMatching(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.assetMatching, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrMatch = ArrayList()
                        arrBarTitle = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("asset_liability_list")
                        for (i in 0 until arr.length()){
                            val match = Matching(arr.getJSONObject(i))
                            arrBarTitle?.add(match.month)
                            arrMatch?.add(match)
                        }
                        barChartData()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }



    private fun barChartData(){
        val groupSpace = 0.08f
        val barSpace = 0.01f // x4 DataSet
        val barWidth = 0.1f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val groupCount = arrMatch!!.size
        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()
        for (i in 0 until groupCount){
            val match = arrMatch!![i]
            values1.add(BarEntry(i.toFloat(),  match.assets.toFloat()))
            values2.add(BarEntry(i.toFloat(), match.liabilities.toFloat()))
        }

        var set1: BarDataSet? = null
        var set2: BarDataSet? = null
        if (barChartAsset?.data != null && barChartAsset?.data?.dataSetCount!! > 0) {
            set1 = barChartAsset?.data!!.getDataSetByIndex(0) as (BarDataSet)
            set2 = barChartAsset?.data!!.getDataSetByIndex(1) as (BarDataSet)
            set1.values = values1
            set2.values = values2
            barChartAsset?.data?.notifyDataChanged()
            barChartAsset?.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values1, "Assets")
            set1.color = Color.parseColor("#21C6B7")

            set2 = BarDataSet(values2, "Liabilities")
            set2.color = Color.parseColor("#F3B62C")
            val data = BarData(set1, set2)
            barChartAsset?.data = data
        }

         val xAxis = barChartAsset?.xAxis
         xAxis?.valueFormatter =  ValueFormatter(arrBarTitle)
        xAxis?.granularity = 1F

        val yAxisRight = barChartAsset?.axisRight
        yAxisRight?.isEnabled = false

        barChartAsset?.barData?.barWidth = barWidth
        barChartAsset?.xAxis?.axisMinimum = 0f
        barChartAsset?.xAxis?.axisMaximum =  barChartAsset?.barData?.getGroupWidth(groupSpace, barSpace)!! * groupCount
        barChartAsset?.groupBars(0f, groupSpace, barSpace)
        barChartAsset?.animateY(1500)
        barChartAsset?.description = null
        barChartAsset?.setExtraOffsets(0f,0f,0f,20f)
        barChartAsset?.axisLeft?.setDrawLabels(true)
        barChartAsset?.xAxis?.setDrawLabels(true)
        barChartAsset?.xAxis?.setDrawGridLines(false)
        barChartAsset?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        barChartAsset?.legend?.isEnabled = true
        barChartAsset?.axisRight?.setDrawLabels(false)
        barChartAsset?.invalidate()
    }

    private fun setCumulativeSurplus(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.cumulativeSurplus, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrCumulative = ArrayList()
                        arrCumulativeTitle = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("cumulative_surp_list")
                        for (i in 0 until arr.length()){
                            val cum = Cumulative(arr.getJSONObject(i))
                            arrCumulativeTitle?.add(cum.month)
                            arrCumulative?.add(cum)
                        }
                        lineChartData()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun lineChartData(){
        val entries = ArrayList<Entry>()
        for (i in 0 until arrCumulative!!.size){
            entries.add(Entry(i.toFloat(), arrCumulative!![i].value.toFloat()))
        }

        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = lineChartSurplus
        lineChartSurplus?.marker = mv

        Collections.sort(entries, EntryXComparator())
        val set = LineDataSet(entries, "DataSet 1")
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.circleHoleColor = Color.parseColor("#21C6B7")
        set.color = Color.parseColor("#21C6B7")
        set.setCircleColor(Color.parseColor("#21C6B7"))
        val data = LineData(set)

        val xAxis = lineChartSurplus?.xAxis
        xAxis?.valueFormatter = ValueFormatter(arrCumulativeTitle)
        xAxis?.granularity = 1f

        lineChartSurplus?.animateY(1500)
        lineChartSurplus?.setDrawMarkers(true)

        val yAxisRight = lineChartSurplus?.axisRight
        yAxisRight?.isEnabled = false

        lineChartSurplus?.xAxis?.axisMinimum = -0.1f
        lineChartSurplus?.xAxis?.setDrawGridLines(false)
        lineChartSurplus?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        lineChartSurplus?.axisRight?.setDrawLabels(false)
        lineChartSurplus?.description?.isEnabled = false
        lineChartSurplus?.legend?.isEnabled = false
        lineChartSurplus?.data = data
        lineChartSurplus?.invalidate()
    }

    private fun setAssetList(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.assets, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        mTotal = 0f
                        arrAssetKeys = ArrayList()
                        val data    = JSONObject(response).obj("message_data")
                        val obj     = data.obj("asset_item_dict")
                        val total   = data.double("total").toFloat()
                        mTotal      = total
                        tvAssetsTotal?.text = String.format("%.2f", total)
                        setLiabilityList()
                        val iter: Iterator<String> = obj.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                                val keyValue = ValueKey(key, obj.getString(key))
                                arrAssetKeys?.add(keyValue)
                                if (arrAssetKeys!!.isNotEmpty()){
                                    rvAssetLiability?.layoutManager = LinearLayoutManager(context)
                                    rvAssetLiability?.adapter = AssetAdapter(context, arrAssetKeys)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
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

    private fun setLiabilityList(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.liabilities, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrLiabilitesKeys = ArrayList()
                        val data    = JSONObject(response).obj("message_data")
                        val obj     = data.obj("liability_item_dict")
                        val total   = data.double("total").toFloat()
                        tvLiabilitiesTotal?.text = String.format("%.2f", total)

                        val surplus = total - mTotal
                        mTotal = surplus
                        tvSurplus?.text = String.format("%.2f", mTotal)

                        val iter: Iterator<String> = obj.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                                val keyValue = ValueKey(key, obj.getString(key))
                                arrLiabilitesKeys?.add(keyValue)
                                if (arrLiabilitesKeys!!.isNotEmpty()){
                                    rvLiability?.layoutManager = LinearLayoutManager(context)
                                    rvLiability?.adapter = AssetAdapter(context, arrLiabilitesKeys)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
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

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}