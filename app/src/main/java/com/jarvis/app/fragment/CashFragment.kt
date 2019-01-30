package com.jarvis.app.fragment

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
import com.jarvis.app.extension.double
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.fragment.unused.CashPlacementDetailFragment
import com.jarvis.app.fragment.unused.ListDetailsFragment
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.CashPlacement
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_cash.*
import kotlinx.android.synthetic.main.layout_cash_flow_summary.*
import kotlinx.android.synthetic.main.layout_cash_movement.*
import kotlinx.android.synthetic.main.layout_cash_placement.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CashFragment : BaseFragment() {
    var arrCashFlow: ArrayList<ValueKey>? = ArrayList()
    var arrCashPlacement: ArrayList<CashPlacement>? = ArrayList()
    var arrWeek: ArrayList<String>? = ArrayList()
    var arrCashMovement: ArrayList<ValueKey>? = ArrayList()

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
    }

    fun title() {
        mActivity?.toolbar?.title = "Cash Position"
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