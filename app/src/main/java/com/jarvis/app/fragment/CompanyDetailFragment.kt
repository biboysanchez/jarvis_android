package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.AssetAdapter
import com.jarvis.app.extension.double
import com.jarvis.app.extension.obj
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_company_detail.*
import org.json.JSONException
import org.json.JSONObject

class CompanyDetailFragment : Fragment() {
    private var mActivity:MainActivity? = null
    private var arrAssetKeys:ArrayList<ValueKey>?       = ArrayList()
    private var arrLiabilitesKeys:ArrayList<ValueKey>?  = ArrayList()
    private var mTotal = 0f

    companion object {
        val TAG = "CompanyDetailFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_company_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = context as MainActivity?
        mActivity?.title = mActivity?.viewModel?.selectedCompany?.company
        mActivity?.showBackButton(true)
        setAssetList()
    }

    private fun setAssetList(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.assets, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                try {
                    try {
                        mTotal = 0f
                        arrAssetKeys = ArrayList()
                        val data    = JSONObject(response).obj("message_data")
                        val obj     = data.obj("asset_item_dict")
                        val total   = data.double("total").toFloat()
                        mTotal      = total
                        tvAssetsTotal.text = String.format("%.2f", total)
                        setLiabilityList()
                        val iter: Iterator<String> = obj.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                                val keyValue = ValueKey(key, obj.getString(key))
                                arrAssetKeys?.add(keyValue)
                                if (arrAssetKeys!!.isNotEmpty()){
                                    rvAssetLiability.layoutManager = LinearLayoutManager(context)
                                    rvAssetLiability.adapter = AssetAdapter(context, arrAssetKeys)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
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
                        tvAssetsTotal?.text = String.format("%.2f", total)

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

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
        mActivity?.title = mActivity?.viewModel?.title
    }
}