package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.adapter.PieLegendAdapter
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.https.API
import com.jarvis.app.https.API.weekList
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Pie
import com.jarvis.app.model.PieModel
import com.jarvis.app.model.Table1
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
    private var arrPortfolioList:ArrayList<String>? = ArrayList()

    private var arrPieTop:ArrayList<PieModel>? = ArrayList()
    private var arrPieBottom:ArrayList<PieModel>? = ArrayList()
    private var mActivity:MainActivity? = null
    private var tablePerformance:ArrayList<Table1>? = ArrayList()

    private var selectedWeek = ""
    private var selectedSpinner1 = ""
    private var selectedSpinner2 = ""

    private var selectedPerformance = 0

    companion object {
        val TAG = "HomeFragment"
        var instance: HomeFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = context as MainActivity?

        Handler().postDelayed({
            spinnerDefaultList()

            getWeekList()
//            setInvestmentDecision()
//            setInvestmentSelection()
//            setTop10Position()
        },300)
    }

    /**
     * Refresh data when change company name
     */
    fun refreshAll(){
        getPieData()
        getPieData2()
        getPerformanceSummary()
    }

    private fun spinnerDefaultList(){
        spinnerSummary?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        spinnerSummary?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedPerformance = position
                rvPerformance?.layoutManager = LinearLayoutManager(context)
                rvPerformance?.adapter = HomeListAdapter(context, tablePerformance, selectedPerformance)
            }
        }
    }

    private fun setInvestmentDecision(){
        spinnerDecision?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerDecision)
        rvDecision?.layoutManager = LinearLayoutManager(context)
      //  rvDecision?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun setInvestmentSelection(){
        spinnerSelection?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerSelection)
        rvSelection?.layoutManager = LinearLayoutManager(context)
       // rvSelection?.adapter = HomeListAdapter(context, ArrayList())
    }

    private fun setTop10Position(){
        spinnerPosition?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR"))
        Util.changeTextColor(spinnerPosition)
        rvPosition?.layoutManager = LinearLayoutManager(context)
      //  rvPosition?.adapter = HomeListAdapter(context, ArrayList())
    }

    /**
     * 1st API call get all available week list
     */
    private fun getWeekList(){
        ApiRequest.postNoUI(context!!, API.weekList, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        val weekList = ArrayList<String>()
                        val arr = JSONObject(response).obj("message_data").arr("portfolio_overview_week_list")
                        if (arr.length() > 0){

                            for (i in 0 until arr.length()){
                                weekList.add(arr.getString(i))
                            }

                            spinnerWeek?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, weekList)
                            spinnerWeek?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                                    selectedWeek = weekList[position]

                                    if (arrPortfolioList?.size!! == 0){
                                        getPortfolioDropdownList()
                                        getPerformanceSummary()
                                    }else{
                                        refreshAll()
                                    }
                                }
                            }
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

    /**
     * 2nd API call, get all dropdown list for portfolio
     */
    private fun getPortfolioDropdownList(){
        ApiRequest.postNoUI(context!!, API.portfolioDropDownList, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrPortfolioList = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("portfolio_overview_category_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                arrPortfolioList?.add(arr.getString(i))
                            }
                            setSpinners()
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

    /**
     * Drop down spinners
     */
    private fun setSpinners() {
        spinnerAssetClass?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, arrPortfolioList!!)
        spinnerAssetClass?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedSpinner1 = arrPortfolioList!![position]
                getPieData()
            }
        }

        spinnerCompanyType?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, arrPortfolioList!!)
        spinnerCompanyType?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedSpinner2 = arrPortfolioList!![position]
                getPieData2()
            }
        }
    }

    fun getPieData(){
        val params = HashMap<String, String>()
        params["category"]  = selectedSpinner1
        params["company"]   = mActivity?.viewModel?.selectedCompany!!
        params["week_id"]   = selectedWeek
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
                        }else{
                            noData(pieChartHome, rvPieLegend)
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

    fun getPieData2(){
        val params = HashMap<String, String>()
        params["category"]  = selectedSpinner2
        params["company"]   = mActivity?.viewModel?.selectedCompany!!
        params["week_id"]   = selectedWeek
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
                        }else{
                            noData(pie2, rvPie2Legend)
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

    private fun noData(pie:com.github.mikephil.charting.charts.PieChart?,
                       recyclerView: RecyclerView?){
        PieChart(activity, pie, ArrayList())
        pie?.centerText = "No data results for \n$selectedWeek"
        pie?.setCenterTextColor(Color.parseColor("#BDBDBD"))
        pie?.invalidate()
        recyclerView?.layoutManager = GridLayoutManager(context,  2)
        recyclerView?.adapter = PieLegendAdapter(context, ArrayList())
    }

    private fun getPerformanceSummary(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.viewModel?.selectedCompany!!
        params["week_id"]   = selectedWeek
        ApiRequest.postNoUI(context!!, API.performanceSummary, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        Log.i(TAG, "==> response")
                        tablePerformance = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("performance_summary_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val performance = Table1(arr.getJSONObject(i))
                                tablePerformance?.add(performance)
                            }

                            rvPerformance?.layoutManager = LinearLayoutManager(context)
                            rvPerformance?.adapter = HomeListAdapter(context, tablePerformance, selectedPerformance)
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

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}