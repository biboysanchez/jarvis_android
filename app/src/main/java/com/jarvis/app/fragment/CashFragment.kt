package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jarvis.app.R
import com.jarvis.app.adapter.CashPlacementAdapter
import com.jarvis.app.adapter.SimpleRecyclerAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.CashPlacement
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_cash.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class CashFragment : BaseFragment() {
    var arrChart: ArrayList<ValueKey>? = ArrayList()
    var arrWeek: ArrayList<String>? = ArrayList()

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "CashPositionFragment"
        var instance: CashFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_cash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCashPosition()

        Handler().postDelayed({
            getCashMovementWeek()
        },100)
    }

    fun title() {
        mActivity?.toolbar?.title = "Cash Position"
    }

    private fun getCashMovementWeek() {
        val params = java.util.HashMap<String, String>()
        params["company"] = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.cashMovementWeek, params, object : ApiRequest.URLCallback {
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)) {
                    try {
                        arrWeek = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("weeks_list")
                        for (i in 0 until arr.length()) {
                            Log.i(TAG, arr.getString(i))
                            arrWeek?.add(arr.getString(i))
                        }
                        setSpinner()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setSpinner() {
        spinnerWeek?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, arrWeek!!)
        spinnerWeek?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                    getCashMovement(arrWeek?.get(position)!!)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getCashPosition() {
        val jsonArray = JSONArray()
        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Capital")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Central Asia")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank CIMB Niaga")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Danamon")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Indonesia")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Mandiri")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Mayapada")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Maybank")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Negara Indonesia")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 0.0)
            put("avg_yield", 0.0)
            put("bank", "Bank Permata")
        })

        jsonArray.put(JSONObject().apply {
            put("_id", "1")
            put("amount", 893.42)
            put("avg_yield", 2.0)
            put("bank", "Bank Sinarmas")
        })


        val arrayList = ArrayList<CashPlacement>()
        for (i in 0 until jsonArray.length()){
            arrayList.add(CashPlacement(jsonArray.getJSONObject(i)))
        }

        rvCashPlacement?.layoutManager = LinearLayoutManager(context)
        rvCashPlacement?.adapter = CashPlacementAdapter(context, arrayList, true)
    }

    private fun getCashMovement(week: String) {
        val params = HashMap<String, String>()
        params["company"] = mActivity?.selectedCompany!!
        params["week"] = week
        ApiRequest.post(context!!, API.cashMovement, params, object : ApiRequest.URLCallback {
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)) {
                    try {
                        arrChart = ArrayList()
                        val arrString = ArrayList<String>()
                        val arr = JSONObject(response).obj("message_data").arr("cashflow_list")
                        for (i in 0 until arr.length()) {
                            val obj = arr.getJSONObject(i)
                            val keyValue = ValueKey(obj.string("label"), obj.string("value"))
                            arrChart?.add(keyValue)
                            arrString.add(keyValue.key)
                            Log.i(TAG, "ValueKey: ${keyValue.key} value:${keyValue.value}")
                        }
                        barChartNegative?.invalidate()
                        setBarChartNegative(arrChart!!)
                        setRecyclerView(arrString)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })

    }

    private fun setRecyclerView(arr:ArrayList<String>){
        rvCashMovement?.layoutManager = GridLayoutManager(context, 2)
        rvCashMovement?.adapter = SimpleRecyclerAdapter(context, arr)
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
        for (i in 0 until mArrCashMovement.size) {
            data.add(Data(i.toFloat() + 1f, mArrCashMovement[i].value.toFloat()))
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