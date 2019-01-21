package com.jarvis.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import kotlinx.android.synthetic.main.fragment_portfolio_overview.*
import org.json.JSONObject

class PortfolioOverviewFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "PortfolioOverviewFragment"
        var instance: PortfolioOverviewFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_portfolio_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        investmentPortfolioSpinner()
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    private fun investmentPortfolioSpinner(){
        spinnerAssetClass?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item, StaticData.dropdown())
    }

    private object StaticData{
        fun dropdown():List<String>{
            return arrayListOf("Asset class", "Sector", "Company type", "Rating", "Duration")
        }

        fun assetClassPie(){
            val json =  JSONObject().apply {
                put("portfolio", "")
            }

//            Mutual funds	 $ 1,875.08
//            Equity	 $ 1,472.09
//            Fixed income	 $ 9,134.66
//            Cash and CE	 $ 751.71

//            val period      = json.string("period")
//            val weekId      = json.string("week_id")
//            val company     = json.string("company")
//            val category    = json.string("category")
//            val item        = json.string("item")
//            val percentage  = json.double("percentage")
//            val portofolio  = json.double("portfolio")
//            val timeStamp   = json.int("rec_timestamp")
//            val pKey        = json.string("pkey")
//            var color       = 0
        }
    }

}