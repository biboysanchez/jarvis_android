package com.jarvis.app.fragment.unused_old

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
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
import com.jarvis.app.adapter.unused.PieLegendAdapter
import com.jarvis.app.adapter.home.*
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.*
import com.jarvis.app.utils.ColorUtil
import com.jarvis.app.utils.JSONUtil
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_investment_selection.*
import kotlinx.android.synthetic.main.layout_investment_decision.*
import kotlinx.android.synthetic.main.layout_performance_summary.*
import kotlinx.android.synthetic.main.layout_top_10.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import com.jarvis.app.utils.CustomBottomSheet


class HomeFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    private var arrPortfolioList:ArrayList<String>? = ArrayList()

    private var arrPieTop:ArrayList<PieModel>? = ArrayList()
    private var arrPieBottom:ArrayList<PieModel>? = ArrayList()
   // private var mActivity:MainActivity? = null

    private var tablePerformance:ArrayList<Table1>? = ArrayList()
    private var tableSecurities:ArrayList<Table2>? = ArrayList()
    private var tableTopTen:ArrayList<Table3>? = ArrayList()

    private var adapterPerformance:PerformanceSummaryAdapter? = null
    private var adapterTopTen:TopTenAdapter? = null
    private var adapterSecurity:SecuritySelectionAdapter? = null
    private var adapterAllocation:AssetAllocationAdapter? = null

    private var underWeightList:ArrayList<Table4>? = ArrayList()
    private var overWeightList:ArrayList<Table4>?  = ArrayList()

    private var summaryArrayAdapter:ArrayAdapter<String>? = null
    private var securityAdapter:ArrayAdapter<String>? = null
    private var top10Adapter:ArrayAdapter<String>? = null

    private var selectedPerformance = 0
    private var selectedSecurities  = 0
    private var selectedTopTen      = 0
    private var selectedWeight      = 0


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
        },300)

        tvShowAll?.setOnClickListener {
            mActivity?.viewModel?.sAdapter = summaryArrayAdapter
            mActivity?.viewModel?.fragmentTag = "Performance Summary"
            mActivity?.viewModel?.list = tablePerformance
            mActivity?.addFragment(ListDetailsFragment(), ListDetailsFragment.TAG)
        }

        tvShowAllSelection?.setOnClickListener {
            mActivity?.viewModel?.sAdapter = securityAdapter
            mActivity?.viewModel?.fragmentTag = "Securities Selection"
            mActivity?.viewModel?.list = tableSecurities
            mActivity?.addFragment(ListDetailsFragment(), ListDetailsFragment.TAG)
        }

        tvShowAllPosition?.setOnClickListener {
            mActivity?.viewModel?.sAdapter = top10Adapter
            mActivity?.viewModel?.fragmentTag = "Top 10 Position"
            mActivity?.viewModel?.list = tableTopTen
            mActivity?.addFragment(ListDetailsFragment(), ListDetailsFragment.TAG)
        }

        imgMenuSummary?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortPerformance!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortPerformance = selectedSorted
                    setPerformanceAdapter(selectedPerformance, selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        imgMenuPosition?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortTopTen!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortTopTen = selectedSorted
                    setTopTenAdapter(selectedTopTen, selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        imgMenuSelection?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortSecurity!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortSecurity = selectedSorted
                    setSecurities(selectedSecurities, selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        imgMenuDecision?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortAssetAllocation!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortAssetAllocation = selectedSorted
                    setAssetAllocation(selectedWeight, selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)

        }
    }

    /**
     * Refresh data when change company name
     */
    fun refreshAll(){
        Log.i(TAG, "View model Company: ${mActivity?.selectedCompany}")
        Log.i(TAG, "View model week: ${mActivity?.selectedWeek}")

        getPieData()
        getPieData2()
        getPerformanceSummary()
        getSecuritiesSelection()
        getTopTen()
        getAssetAllocation()
    }

    private fun spinnerDefaultList(){
        summaryArrayAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
            Table1.table1DropdownList())
        spinnerSummary?.adapter = summaryArrayAdapter
        spinnerSummary?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedPerformance = position
                setPerformanceAdapter(selectedPerformance, mActivity?.sortPerformance!!)
            }
        }

        securityAdapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
            Table2.table2DropDownList())
        spinnerSelection?.adapter =  securityAdapter
        Util.changeTextColor(spinnerSelection)
        spinnerSelection?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedSecurities = position
                setSecurities(selectedSecurities, mActivity?.sortSecurity!!)
            }
        }

        top10Adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
            Table3.table3DropDownList())
        spinnerPosition?.adapter = top10Adapter
        spinnerPosition?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedTopTen = position
                setTopTenAdapter(selectedTopTen, mActivity?.sortTopTen!!)

            }
        }

        spinnerDecision?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,
            Table4.table3DropDownList())
        spinnerDecision?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedWeight = position
                setAssetAllocation(selectedWeight, mActivity?.sortAssetAllocation!!)
            }
        }
    }

    private fun setPerformanceAdapter(selPerformance:Int, sorter:Int){
        rvPerformance?.layoutManager = LinearLayoutManager(context)
        adapterPerformance= PerformanceSummaryAdapter(
            context,
            tablePerformance,
            selPerformance,
            false
        )

        adapterPerformance?.sortPerformance(sorter)
        rvPerformance?.adapter = adapterPerformance
    }


    private fun setTopTenAdapter(selAdapter:Int, sorter: Int){
        rvPosition?.layoutManager = LinearLayoutManager(context)
        adapterTopTen = TopTenAdapter(
            context,
            tableTopTen,
            selAdapter,
            false
        )
        adapterTopTen?.sortPerformance(sorter)
        rvPosition?.adapter = adapterTopTen
    }

    private fun setSecurities(selSecurity:Int, sorter:Int){
        rvSelection?.layoutManager = LinearLayoutManager(context)
        adapterSecurity = SecuritySelectionAdapter(
            context,
            tableSecurities,
            selSecurity,
            false
        )

        adapterSecurity?.sortSecurity(sorter)
        rvSelection?.adapter = adapterSecurity
    }

    private fun setAssetAllocation(spinnerIndex:Int, sortIndex:Int){
        rvDecision?.layoutManager = LinearLayoutManager(context)
        if (spinnerIndex == 0){
            adapterAllocation =  AssetAllocationAdapter(context, underWeightList, selectedWeight)
        }else{
            adapterAllocation = AssetAllocationAdapter(context, overWeightList, selectedWeight)
        }

        adapterAllocation?.sortAssetAllocation(sortIndex)
        rvDecision?.adapter = adapterAllocation
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
                                    mActivity?.selectedWeek = weekList[position]

                                    if (arrPortfolioList?.size!! == 0){
                                        getPortfolioDropdownList()
                                        getPerformanceSummary()
                                        getSecuritiesSelection()
                                        getTopTen()
                                        getAssetAllocation()
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
                mActivity?.selectedCategory1 = arrPortfolioList!![position]
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
                mActivity?.selectedCategory2 = arrPortfolioList!![position]
                getPieData2()
            }
        }
    }

    fun getPieData(){
        val params = HashMap<String, String>()
        params["category"]  = mActivity?.selectedCategory1!!
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
        ApiRequest.post(mActivity!!, API.pieAssetClass, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(mActivity!!, response)){
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
        params["category"]  = mActivity?.selectedCategory2!!
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
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
                            rvPie2Legend?.adapter       =
                                    PieLegendAdapter(context, arrPieBottom)
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
        pie?.centerText = "No data results for \n${mActivity?.selectedWeek}"
        pie?.setCenterTextColor(Color.parseColor("#BDBDBD"))
        pie?.invalidate()
        recyclerView?.layoutManager = GridLayoutManager(context,  2)
        recyclerView?.adapter = PieLegendAdapter(context, ArrayList())
    }

    private fun getPerformanceSummary(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
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

                            setPerformanceAdapter(selectedPerformance, mActivity?.sortPerformance!!)

                        }else{
                            setPerformanceAdapter(selectedPerformance, mActivity?.sortPerformance!!)
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

    private fun getSecuritiesSelection(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
        ApiRequest.postNoUI(context!!, API.securitySelection, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {

                        tableSecurities = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("securities_selection_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val security = Table2(arr.getJSONObject(i))
                                Log.i(TAG, "==> ${arr.getJSONObject(i)}")
                                tableSecurities?.add(security)
                            }

                            setSecurities(selectedSecurities, mActivity!!.sortSecurity)

                        }else{
                            setSecurities(selectedSecurities, mActivity!!.sortSecurity)
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

    private fun getTopTen(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
        ApiRequest.postNoUI(context!!, API.topTen, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {

                        tableTopTen = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("top_10_position_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val topTen = Table3(arr.getJSONObject(i))
                                Log.i(TAG, "==> ${arr.getJSONObject(i)}")
                                tableTopTen?.add(topTen)
                            }

                            setTopTenAdapter(selectedTopTen, mActivity?.sortTopTen!!)
                        }else{
                            setTopTenAdapter(selectedTopTen, mActivity?.sortTopTen!!)
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

    private fun getAssetAllocation(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week_id"]   = mActivity?.selectedWeek!!
        ApiRequest.postNoUI(context!!, API.assetAllocation, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {

                        underWeightList = ArrayList()
                        overWeightList  = ArrayList()
                        val arrUnderWeight = JSONObject(response).obj("message_data").obj("asset_allocation_dict").arr("UNDERWEIGHT")
                        val arrOverWeight  = JSONObject(response).obj("message_data").obj("asset_allocation_dict").arr("OVERWEIGHT")
                        if (arrUnderWeight.length() > 0){
                            for (i in 0 until arrUnderWeight.length()){
                                val underweight = Table4(arrUnderWeight.getJSONObject(i))
                                underWeightList?.add(underweight)
                            }

                            setAssetAllocation(selectedWeight, mActivity?.sortAssetAllocation!!)
                        }else{
                            setAssetAllocation(selectedWeight, mActivity?.sortAssetAllocation!!)
                        }

                        if (arrOverWeight.length() > 0){
                            for (i in 0 until arrOverWeight.length()){
                                val overWeight = Table4(arrOverWeight.getJSONObject(i))
                                overWeightList?.add(overWeight)
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


    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}