package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.R.id.rvSelection
import com.jarvis.app.adapter.CashFlowAdapter
import com.jarvis.app.adapter.CashPlacementAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.CashPlacement
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import com.jarvis.app.utils.Util
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

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "CashPositionFragment"
        var instance:CashPositionFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_cash_position, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshAll()
        setSpinner()
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

    private fun setSpinner(){
        spinnerCashMovement?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(
            "Week 1 - Sep 2018", "Week 2 - Sep 2018", "Week 3 - Sep 2018", "Week 4 - Sep 2018"))
        spinnerCashMovement?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        Util.changeTextColor(spinnerCashMovement, "#9E9E9E")
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}