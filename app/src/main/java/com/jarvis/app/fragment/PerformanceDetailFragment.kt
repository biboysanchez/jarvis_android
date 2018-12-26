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
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.R
import com.jarvis.app.adapter.LeaseAdapter
import com.jarvis.app.adapter.TimeSeriesAdapter
import com.jarvis.app.custom.MarkerLiquidProfile
import com.jarvis.app.custom.MyMarkerView
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.toast
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.PerformanceDetail
import com.jarvis.app.model.Table6
import com.jarvis.app.model.TableRisk
import com.jarvis.app.utils.CustomBottomSheet
import com.jarvis.app.utils.JSONUtil
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_performance_detail.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PerformanceDetailFragment: BaseFragment() {
    private lateinit var mView:View
    private var arrLiquidProfile: ArrayList<ArrayList<PerformanceDetail>>? = ArrayList()
    private var arrLiquidTitle:ArrayList<String>? = ArrayList()

    private var arrRiskMeasureAllList:ArrayList<TableRisk>? = ArrayList()
    private var arrRiskMeasure:ArrayList<TableRisk>? = ArrayList()
    private var hashRiskMeasure:HashMap<String, ArrayList<TableRisk>>? = HashMap()
    private val arrPortfolioList = Arrays.asList("Danamas Saham", "Simas Saham Bertumbuh")
    private var selectedPortfolio = ""
    private var selectedLease = 0
    private var selectedPerformance = 0


    private var arrLease:ArrayList<Table6>? = ArrayList()
    private var leaseArrayAdapter:ArrayAdapter<String>? = null

    private var leaseAdapter:LeaseAdapter? = null
    private var performanceRiskAdapter:TimeSeriesAdapter? = null

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    fun title(){
        mActivity?.toolbar?.title = "Detail View"
    }

    companion object {
        const val TAG = "PerformanceDetailFragment"
        var instance:PerformanceDetailFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        mView = inflater.inflate(R.layout.fragment_performance_detail, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshAll()
        setSpinners()
    }

    private fun setSpinners(){
        leaseArrayAdapter =  ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item, Table6.table6DropdownList())

        spinnerLiquidPortfolio?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, arrPortfolioList)

        spinnerLiquidPortfolio?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedPortfolio = arrPortfolioList[position]
                getLeaseLiquid()
            }
        }

        spinnerLiquidProfile?.adapter = leaseArrayAdapter
        spinnerLiquidProfile?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                selectedLease = position
                setLeaseAdapter(mActivity?.sortLease!!)
            }
        }

        tvShowAllLiquiedProfile?.setOnClickListener {
            mActivity?.viewModel?.sAdapter = leaseArrayAdapter
            mActivity?.viewModel?.fragmentTag = "Lease Liquid Securities"
            mActivity?.viewModel?.list = arrLease
            mActivity?.addFragment(ListDetailsFragment(), ListDetailsFragment.TAG)
        }

        imgLiquidProfile?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortLease!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortLease = selectedSorted
                    setLeaseAdapter(selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)

        }

        imgMenuDecision?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortRiskManagement!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mActivity?.sortRiskManagement = selectedSorted
                    setPerformance(selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)

        }
    }

    fun refreshAll(){
        getLiquidityProfile()
        getPerformanceRiskAndManagement()

        if (selectedPortfolio.isNotEmpty()){
            getLeaseLiquid()
        }
    }

    private fun getLiquidityProfile(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.liquidityProfile, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        val arr = JSONObject(response).obj("message_data").arr("chart_data_list")
                        arrLiquidProfile = ArrayList()
                        arrLiquidTitle = ArrayList()

                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                val list:ArrayList<PerformanceDetail> = ArrayList()
                                val jsonArray = arr.getJSONArray(i)

                                for (z in 0 until jsonArray.length()){
                                    val arrDetail = PerformanceDetail(jsonArray.getJSONObject(z))
                                    list.add(arrDetail)
                                }

                                if (list.isNotEmpty()){
                                    arrLiquidProfile?.add(list)
                                }
                            }

                            if (arrLiquidProfile!!.isNotEmpty()){
                                setStackBarChart()
                            }else{
                                barChartLiquidity?.clear()
                                barChartLiquidity?.notifyDataSetChanged()
                            }
                        }else{
                            barChartLiquidity?.invalidate()
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

    private fun setStackBarChart(){
        barChartLiquidity?.setPinchZoom(false)
        barChartLiquidity?.setDrawValueAboveBar(false)
        barChartLiquidity?.isHighlightFullBarEnabled = false
        barChartLiquidity?.setExtraOffsets(0f,0f,0f,20f)
        barChartLiquidity?.xAxis?.setDrawGridLines(false)
        barChartLiquidity?.xAxis?.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis = barChartLiquidity?.axisLeft
        leftAxis?.axisMinimum = 0f
        barChartLiquidity?.axisRight?.isEnabled = false

        val xLabels = barChartLiquidity?.xAxis
        xLabels?.position = XAxis.XAxisPosition.BOTTOM

        val l = barChartLiquidity?.legend
        l?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l?.setDrawInside(false)
        l?.formSize = 8f
        l?.formToTextSpace = 4f
        l?.xEntrySpace = 6f

        val values1 = ArrayList<BarEntry>()
        val values2 = ArrayList<BarEntry>()
        arrLiquidTitle = ArrayList()
        var set1: BarDataSet? = null
        var set2: BarDataSet? = null
        var title1 = ""
        var title2 = ""

        for (i in 0 until arrLiquidProfile!!.size){
            val arr = arrLiquidProfile?.get(i)
          // arr?.sortWith(Comparator { o1, o2 -> o1?.saham!!.compareTo(o2!!.saham) })
            for (z in 0 until arr!!.size){
                val performanceDetail = arr[z]
                if (performanceDetail.portfolio == "Danamas Saham"){
                    values2.add(BarEntry(i.toFloat(), performanceDetail.saham.toFloat()))
                    title2 = performanceDetail.portfolio
                    set2 = BarDataSet(values2, title2)
                    set2.color = Color.parseColor("#21C6B7")
                    set2.setDrawValues(false)
                }else{
                    values1.add(BarEntry(i.toFloat(), performanceDetail.saham.toFloat()))
                    title1 = performanceDetail.portfolio
                    set1 = BarDataSet(values1, title1)
                    set1.color = Color.parseColor("#F3B62C")
                    set1.setDrawValues(false)
                }

                if (!arrLiquidTitle!!.contains(performanceDetail.target)){
                    arrLiquidTitle?.add(performanceDetail.target)
                }
            }
        }

        val mv = MarkerLiquidProfile(context,R.layout.custom_marker_liquid_profile)
        mv.chartView = barChartLiquidity
        barChartLiquidity?.marker = mv
        val tvTop = mv.findViewById<TextView>(R.id.tvTop)
        val tvSub = mv.findViewById<TextView>(R.id.tvBottom)
        barChartLiquidity?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val index = h?.x
                val d = arrLiquidProfile?.get(index?.toInt()!!)
                for (z in 0 until d!!.size) {
                    val performanceDetail = d[z]
                    if (performanceDetail.portfolio == "Danamas Saham"){
                        tvSub.text = String.format("%.0f%s", (performanceDetail.saham*100), "%")
                    }else{
                        tvTop.text = String.format("%.0f%s", (performanceDetail.saham*100), "%")
                    }
                }
            }
        })

        val xAxis = barChartLiquidity?.xAxis
        xAxis?.granularity = 1f
        xAxis?.valueFormatter = ValueFormatter(arrLiquidTitle)
        xLabels?.setLabelCount(arrLiquidTitle!!.size-1, false)
        val set: BarDataSet
        if (barChartLiquidity?.data != null && barChartLiquidity?.data?.dataSetCount!! > 0) {
            set = barChartLiquidity.data.getDataSetByIndex(0) as BarDataSet
            set.values = values1
           // set.setColors(Color.parseColor("#F3B62C"), Color.parseColor("#21C6B7"))
            barChartLiquidity?.data?.notifyDataChanged()
            barChartLiquidity?.notifyDataSetChanged()
        } else {
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1!!)
            dataSets.add(set2!!)
            val data = BarData(dataSets)
            barChartLiquidity?.data = data
        }

        barChartLiquidity?.animateY(1200)
        barChartLiquidity?.description = null
        barChartLiquidity?.legend?.isEnabled = true
        barChartLiquidity?.isHighlightFullBarEnabled = true
        barChartLiquidity?.setDrawValueAboveBar(true)
        barChartLiquidity?.setFitBars(true)
        barChartLiquidity?.invalidate()
    }

    private fun getPerformanceRiskAndManagement(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.riskMeasurement, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        hashRiskMeasure = HashMap()
                        arrRiskMeasureAllList = ArrayList()

                        val obj = JSONObject(response).obj("message_data").obj("perf_risk_dict")
                        val iter: Iterator<String> = obj.keys()
                        while (iter.hasNext()) {
                            arrRiskMeasure = ArrayList()
                            val key = iter.next()
                            try {
                                val arr = obj.arr(key)
                                for (i in 0 until arr.length()){
                                    val mObj = TableRisk(arr.getJSONObject(i))
                                    mObj.group = key
                                    arrRiskMeasure?.add(mObj)
                                    arrRiskMeasureAllList?.add(mObj)
                                }
                                hashRiskMeasure?.put(key, arrRiskMeasure!!)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        spinnerPerformance?.adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item, TableRisk.tableRiskDropDownList())
                        spinnerPerformance?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
                                selectedPerformance = position
                                setPerformance(mActivity?.sortRiskManagement!!)
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

    private fun getLeaseLiquid(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["portfolio"] = selectedPortfolio
        ApiRequest.postNoUI(context!!, API.leasedLiquid, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrLease = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("lease_liquidity_list")
                        for (i in 0 until arr.length()) {
                            val mObj = Table6(arr.getJSONObject(i))
                            arrLease?.add(mObj)
                        }
                        setLeaseAdapter( mActivity?.sortLease!!)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setLeaseAdapter(sorter:Int){
        rvLiquidProfile?.layoutManager = LinearLayoutManager(context)
        leaseAdapter = LeaseAdapter(
            context,
            arrLease,
            selectedLease,
            false
        )

        rvLiquidProfile?.adapter = leaseAdapter
        leaseAdapter?.sortLeaseLiquidity(sorter)
    }


    private fun setPerformance(sorter:Int){
        rvPerformanceAndRisk?.layoutManager = LinearLayoutManager(context)
        performanceRiskAdapter = TimeSeriesAdapter(
            context, arrRiskMeasureAllList, selectedPerformance, false
        )

        rvPerformanceAndRisk?.adapter = performanceRiskAdapter
        performanceRiskAdapter?.sortPerformanceAndRisk(sorter)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

}