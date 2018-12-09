package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.adapter.PieLegendAdapter
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.PieModel
import com.jarvis.app.utils.ColorUtil
import com.jarvis.app.utils.JSONUtil
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_invesment_selection.*
import kotlinx.android.synthetic.main.layout_investment_decision.*
import kotlinx.android.synthetic.main.layout_performance_summary.*
import kotlinx.android.synthetic.main.layout_top_10.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private var arrPieTop:ArrayList<PieModel>? = ArrayList()
    private var arrPieBottom:ArrayList<PieModel>? = ArrayList()

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            setSpinners()

            setPerformanceSummary()
            setInvestmentDecision()
            setInvestmentSelection()
            setTop10Position()
        },300)

        spinnerWeek?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(
            "All Week", "Week 1 - Sep 2018", "Week 2 - Sep 2018", "Week 3 - Sep 2018", "Week 4 - Sep 2018"))
        spinnerWeek?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        Util.changeTextColor(spinnerWeek, "#FFFFFF")
    }

    /**
     * Drop down spinners
     */
    private fun setSpinners(){
        val list1 = Arrays.asList("Sectors", "Asset Class", "SMMA")
        val list2 = Arrays.asList("Rating", "Company Type", "SMMA", "Duration")
        spinnerAssetClass?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, list1)
        spinnerAssetClass?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                getPieData(list1[position])
            }
        }

        spinnerCompanyType?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, list2)
        spinnerCompanyType?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                getPieData2(list2[position])
            }
        }
    }

    private fun setPerformanceSummary(){
        spinnerSummary?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerSummary)

        rvPerformance?.layoutManager = LinearLayoutManager(context)
        rvPerformance?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun setInvestmentDecision(){
        spinnerDecision?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerDecision)
        rvDecision?.layoutManager = LinearLayoutManager(context)
        rvDecision?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun setInvestmentSelection(){
        spinnerSelection?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerSelection)
        rvSelection?.layoutManager = LinearLayoutManager(context)
        rvSelection?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun setTop10Position(){
        spinnerPosition?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerPosition)
        rvPosition?.layoutManager = LinearLayoutManager(context)
        rvPosition?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun getPieData(category: String){
        val params = HashMap<String, String>()
        params["category"] = category
        ApiRequest.postNoUI(context!!, API.pieAssetClass, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrPieTop = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("asset_class_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val asst = PieModel(arr.getJSONObject(i))
                                asst.color = ColorUtil.arrColorA()[i]
                                arrPieTop?.add(asst)
                            }

                            PieChart(activity, pieChartHome, arrPieTop)
                            rvPieLegend?.layoutManager = GridLayoutManager(context,  2)
                            rvPieLegend?.adapter       = PieLegendAdapter(context, arrPieTop)
                        }
                    }catch (e:JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun getPieData2(category: String){
        val params = HashMap<String, String>()
        params["category"] = category
        ApiRequest.postNoUI(context!!, API.pieCompany, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrPieBottom = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("company_range_duration_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val asst = PieModel(arr.getJSONObject(i))
                                asst.color = ColorUtil.arrColorB()[i]
                                arrPieBottom?.add(asst)
                            }

                            PieChart(activity, pie2, arrPieBottom)
                            rvPie2Legend?.layoutManager = GridLayoutManager(context,  2)
                            rvPie2Legend?.adapter       = PieLegendAdapter(context, arrPieBottom)
                        }
                    }catch (e:JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }
}