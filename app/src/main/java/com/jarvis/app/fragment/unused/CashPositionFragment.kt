package com.jarvis.app.fragment.unused

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jarvis.app.R
import com.jarvis.app.adapter.CashFlowAdapter
import com.jarvis.app.adapter.CashPlacementAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.CashPlacement
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_cash_flow_summary.*
import kotlinx.android.synthetic.main.layout_cash_movement.*
import kotlinx.android.synthetic.main.layout_cash_placement.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CashPositionFragment : BaseFragment() {
    var arrCashFlow: ArrayList<ValueKey>? = ArrayList()
    var arrCashPlacement:ArrayList<CashPlacement>? = ArrayList()
    var arrWeek: ArrayList<String>? = ArrayList()
    var arrCashMovement:ArrayList<ValueKey>? = ArrayList()

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "CashPositionFragment"
        var instance: CashPositionFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_cash_position, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshAll()
        tvShowAllCashPlacement?.setOnClickListener {
            mActivity?.viewModel?.fragmentTag = "Cash Movement Detail"
            mActivity?.addFragment(CashPlacementDetailFragment.newInstance(arrCashPlacement!!), CashPlacementDetailFragment.TAG)
        }
    }

    fun title(){
        mActivity?.toolbar?.title = "Cash Position"
    }

    fun refreshAll(){
        getCashPosition()
        getCashPlacement()
        getCashMovementWeek()
    }

    private fun getCashPosition(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.cashPosition, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrCashFlow = ArrayList()
                        val json = JSONObject(response).obj("message_data").obj("cashflow_dict")
                        val iter: Iterator<String> = json.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                               val valueKey = ValueKey(key, json.string(key))
                                arrCashFlow?.add(valueKey)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        rvSelection?.layoutManager  = LinearLayoutManager(context)
                        rvSelection?.adapter        = CashFlowAdapter(context, arrCashFlow)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun getCashPlacement(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.cashPlacement, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrCashPlacement = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("cashflow_list")
                        for (i in 0 until arr.length()){
                            arrCashPlacement?.add(CashPlacement(arr.getJSONObject(i)))
                        }
                        rvAssets?.layoutManager = LinearLayoutManager(context)
                        rvAssets?.adapter = CashPlacementAdapter(context, arrCashPlacement, false)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun getCashMovementWeek(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.cashMovementWeek, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrWeek = ArrayList()
                        mActivity?.summaryList = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("weeks_list")
                        for (i in 0 until arr.length()){
                            arrWeek?.add(arr.getString(i))
                            mActivity?.summaryList?.add(arr.getString(i))
                        }
                        setSpinner()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setSpinner(){
        spinnerCashMovement?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, arrWeek!!)
        spinnerCashMovement?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#9E9E9E"))
                getCashMovement(arrWeek?.get(position)!!)
            }
        }
    }

    private fun getCashMovement(week:String){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week"]      = week
        ApiRequest.postNoUI(context!!, API.cashMovement, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrCashMovement = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("cashflow_list")
                        for (i in 0 until arr.length()){
                            val obj = arr.getJSONObject(i)
                            val keyValue = ValueKey(obj.string("label"), obj.string("value"))
                            arrCashMovement?.add(keyValue)
                            Log.i(TAG, "ValueKey: ${keyValue.key} value:${keyValue.value}")
                        }

                        barChartNegative?.invalidate()
                        setBarChartNegative(arrCashMovement!!)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })

        tvShowAllDecision?.setOnClickListener {
            mActivity?.viewModel?.fragmentTag = "Cash Movement Details"
            mActivity?.addFragment(
                ListDetailsFragment(),
                ListDetailsFragment.TAG
            )
        }
    }

    fun setBarChartNegative(mArrCashMovement: ArrayList<ValueKey>) {
        barChartNegative?.extraBottomOffset = 6f

        barChartNegative?.setDrawValueAboveBar(true)
        barChartNegative?.description?.isEnabled = false

        // scaling can now only be done on x- and y-axis separately
        barChartNegative?.setPinchZoom(false)
        barChartNegative?.setDrawGridBackground(false)

        val xAxis = barChartNegative?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)
        xAxis?.setDrawAxisLine(false)
        xAxis?.textSize = 13f
        xAxis?.labelCount = mArrCashMovement.size
        xAxis?.setCenterAxisLabels(true)
        xAxis?.granularity = 1f

        val left = barChartNegative?.axisLeft
        left?.setDrawLabels(false)
        left?.spaceTop = 25f
        left?.spaceBottom = 25f
        left?.setDrawAxisLine(true)
        left?.setDrawGridLines(true)
        left?.setDrawZeroLine(true) // draw a zero line
        left?.zeroLineColor = Color.GRAY
        left?.zeroLineWidth = 0.7f
        barChartNegative?.axisRight?.isEnabled = false
        barChartNegative?.legend?.isEnabled = false

        val data = java.util.ArrayList<Data>()
        for (i in 0 until mArrCashMovement.size){
            data.add(Data(i.toFloat()+1f, mArrCashMovement[i].value.toFloat()))
        }

        setData(data)
    }

    private fun setData(dataList: List<Data>) {
        val values = java.util.ArrayList<BarEntry>()
        val colors = java.util.ArrayList<Int>()

        for (i in dataList.indices) {
            val d = dataList[i]
            val entry = BarEntry(d.xValue, d.yValue)
            values.add(entry)

            // specific colors
            if (d.yValue >= 0)
                colors.add(Color.parseColor("#E8781A"))
            else
                colors.add(Color.parseColor("#21C6B7"))
        }

        val set: BarDataSet
        if (barChartNegative?.data != null && barChartNegative?.data!!.dataSetCount > 0) {
            set = barChartNegative?.data!!.getDataSetByIndex(0) as BarDataSet
            set.values = values
            barChartNegative?.data!!.notifyDataChanged()
            barChartNegative?.notifyDataSetChanged()
        } else {
            set = BarDataSet(values, "Values")
            set.colors = colors
            set.setValueTextColors(colors)

            val data = BarData(set)
            data.setValueTextSize(13f)
            data.barWidth = 0.8f
            barChartNegative?.animateY(1500)
            barChartNegative?.data = data
            barChartNegative?.data?.notifyDataChanged()
            barChartNegative?.notifyDataSetChanged()
            barChartNegative?.invalidate()
        }
    }

    /**
     * Demo class representing data.
     */
    private inner class Data internal constructor(
        internal val xValue: Float,
        internal val yValue: Float
    )

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}