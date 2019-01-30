package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.adapter.unused.PieLegendAdapter
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.model.PieModel
import com.jarvis.app.utils.ColorUtil
import kotlinx.android.synthetic.main.fragment_portfolio_overview.*
import org.json.JSONArray
import org.json.JSONObject

class PortfolioOverviewFragment : BaseFragment() {
    private var arrPieChart:ArrayList<PieModel>? = ArrayList()

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
        setPieChart()
    }

    private fun setPieChart(){
        PieChart(activity, pieChartPortfolioOverview, StaticData.assetClassPie())
        rvPieLegendPortfolio?.layoutManager = LinearLayoutManager(context)
        rvPieLegendPortfolio?.adapter       = PieLegendAdapter(context, StaticData.assetClassPie())
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
            return arrayListOf("Asset class", "Sector", "Company Type", "Rating", "Duration")
        }

        fun assetClassPie(): ArrayList<PieModel>{
            var arr = ArrayList<PieModel>()
            val pieBySector = JSONArray()
            pieBySector.put(JSONObject().apply {
                put("item", "Agriculture")
                put("portfolio", 1.13)
                put("percentage", 0.01)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Basic Industry")
                put("portfolio", 5937.72)
                put("percentage", 44.87)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Cash & Other Unclassified")
                put("portfolio", 2626.80)
                put("percentage", 19.85)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Consumer")
                put("portfolio", 28.19)
                put("percentage", 0.21)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Financials")
                put("portfolio", 1711.54)
                put("percentage", 12.93)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Government")
                put("portfolio", 1814.74)
                put("percentage",13.71)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Infrastructure")
                put("portfolio", 379.38)
                put("percentage", 2.87)
            })
            pieBySector.put(JSONObject().apply {
                put("item", "Mining")
                put("portfolio", 115.62)
                put("percentage", 0.87)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Misc. Industry")
                put("portfolio", 14.35)
                put("percentage", 0.11)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Property")
                put("portfolio", 348.31)
                put("percentage", 2.63)
            })

            pieBySector.put(JSONObject().apply {
                put("item", "Trading")
                put("portfolio", 255.78)
                put("percentage", 1.93)
            })

            for (i in 0 until pieBySector.length()){
                val model =PieModel(pieBySector.getJSONObject(i))
                model.color = ColorUtil.arrColorA()[i]
                arr.add(model)
            }
            return arr
        }
    }

}