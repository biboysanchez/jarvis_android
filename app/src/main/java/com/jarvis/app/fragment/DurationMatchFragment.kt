package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.jarvis.app.adapter.AssetAdapter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Matching
import com.jarvis.app.utils.JSONUtil
import org.json.JSONException
import kotlin.collections.ArrayList


class DurationMatchFragment : BaseFragment() {
    private var arrMatch :ArrayList<Matching>? = ArrayList()

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

       // setAssetList()
       // setLiabilityList()
        setAssetLiabilityMatching()
    }

    private fun setAssetLiabilityMatching(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.assetMatching, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrMatch = ArrayList()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
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
        barChartAsset?.setNoDataText("Loading...")

        val xAxis = barChartAsset?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)

        barChartAsset?.axisLeft?.setDrawGridLines(true)

        // setting data
        barChartData()
        setLineChart()

        // add a nice and smooth animation
        barChartAsset?.animateY(1500)
        barChartAsset?.legend?.isEnabled = false
    }

    private fun barChartData(){
        val groupSpace = 0.08f
        val barSpace = 0.01f // x4 DataSet
        val barWidth = 0.1f // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        val groupCount = 4
        val startYear = 1980
        val endYear = startYear + groupCount

        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()

        val randomMultiplier = 50f

        for (i in startYear until endYear) {
            values1.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
            values2.add(BarEntry(i.toFloat(), (Math.random() * randomMultiplier).toFloat()))
        }

        val set1: BarDataSet
        val set2: BarDataSet

        if (barChartAsset?.data != null && barChartAsset?.data?.dataSetCount!! > 0) {
            set1 = barChartAsset.data.getDataSetByIndex(0) as (BarDataSet)
            set2 = barChartAsset.data.getDataSetByIndex(1) as (BarDataSet)
            set1.values = values1
            set2.values = values2
            barChartAsset.data.notifyDataChanged()
            barChartAsset.notifyDataSetChanged()
        } else {
            // create 4 DataSets
            set1 = BarDataSet(values1, "Asset")
            set1.color = Color.parseColor("#21C6B7")

            set2 = BarDataSet(values2, "Liability")
            set2.color = Color.parseColor("#F3B62C")

            val data = BarData(set1, set2)
            data.setValueFormatter(LargeValueFormatter())
            barChartAsset?.data = data
        }

        // specify the width each bar should have
        barChartAsset?.barData?.barWidth = barWidth

        // restrict the x-axis range
        barChartAsset?.xAxis?.axisMinimum = startYear.toFloat()


        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChartAsset?.xAxis?.axisMaximum = startYear + barChartAsset.barData.getGroupWidth(groupSpace, barSpace) * groupCount
        barChartAsset?.groupBars(startYear.toFloat(), groupSpace, barSpace)

        barChartAsset?.description = null
        barChartAsset?.axisLeft?.setDrawLabels(true)
        barChartAsset?.axisRight?.setDrawLabels(false)
        barChartAsset?.xAxis?.setDrawLabels(false)
        barChartAsset?.legend?.isEnabled = false
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
        lineChartSurplus?.isDragEnabled = false
        lineChartSurplus?.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        lineChartSurplus?.setPinchZoom(false)
        lineChartSurplus?.setDrawMarkers(true)

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = lineChartSurplus // For bounds control
        lineChartSurplus?.marker = mv // Set the marker to the chart

        val xl = lineChartSurplus?.xAxis
        xl?.setAvoidFirstLastClipping(true)
        xl?.axisMinimum = 0f

        val leftAxis = lineChartSurplus?.axisLeft
        leftAxis?.isInverted = false
        leftAxis?.axisMinimum = 0f
        leftAxis?.axisMinimum = 0f // this replaces setStartAtZero(true)

        val rightAxis = lineChartSurplus?.axisRight
        rightAxis?.axisMinimum = 0f
        rightAxis?.isEnabled = true


        // get the legend (only possible after setting data)
        val l = lineChartSurplus?.legend

        // modify the legend ...
        l?.form = Legend.LegendForm.LINE

        // don't forget to refresh the drawing
        lineChartSurplus?.invalidate()

        // add data
        lineChartData()
    }

    private fun lineChartData(){
        val entries = ArrayList<Entry>()
        entries.add(Entry(10f, 42f))
        entries.add(Entry(20f, 71.5f))
        entries.add(Entry(30f, 9.3f))
        entries.add(Entry(40f, 33f))

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")
        set1.lineWidth = 3f
        set1.circleRadius = 4f

        // create a data object with the data sets
        val data = LineData(set1)

        lineChartSurplus?.legend?.isEnabled = false
        // set data
        lineChartSurplus?.data = data
        lineChartSurplus?.invalidate()
    }

    private fun setAssetList(){
        rvAssetLiability?.layoutManager = LinearLayoutManager(context)
        rvAssetLiability?.adapter = AssetAdapter(context, null)
    }

    private fun setLiabilityList(){
        rvLiability?.layoutManager = LinearLayoutManager(context)
        rvLiability?.adapter = AssetAdapter(context, null)
    }
}