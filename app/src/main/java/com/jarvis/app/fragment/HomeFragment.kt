package com.jarvis.app.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.R.id.*
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.adapter.HorizontalListAdapter
import com.jarvis.app.adapter.PieLegendAdapter
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_invesment_selection.*
import kotlinx.android.synthetic.main.layout_investment_decision.*
import kotlinx.android.synthetic.main.layout_performance_summary.*
import kotlinx.android.synthetic.main.layout_top_10.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            setPieChart()
            setSpinners()
            setPieRecyclerView()

            setPerformanceSummary()
            setInvestmentDecision()
            setInvestmentSelection()
            setTop10Position()
        },300)

        spinnerWeek?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(
            "All Week", "Week 1 - Sep 2018", "Week 2 - Sep 2018", "Week 3 - Sep 2018", "Week 4 - Sep 2018"))
        spinnerWeek?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        Util.changeTextColor(spinnerWeek, "#FFFFFF")
        setHorizontalScrollView()
    }

    private fun setHorizontalScrollView() {
        val btnLabels = Arrays.asList("BMA", "SMA", "JIWA", "MISG", "ASM", "BSA")
        rvHorizontal?.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        rvHorizontal?.adapter = HorizontalListAdapter(context, btnLabels)
    }

    /**
     * Drop down spinners
     */
    private fun setSpinners(){
        spinnerAssetClass?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Asset Class", "Sectors"))

        spinnerCompanyType?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Company Type", "Rating", "Duration"))

        Util.changeTextColor(spinnerAssetClass)
        Util.changeTextColor(spinnerCompanyType)
    }

    /**
     * Set pie charts and each data
     */
    private fun setPieChart(){
        val pieChart = PieChart(activity, pieChartHome, StaticData.pieData())
        pieChart.data()

        val pieChart2 = PieChart(activity, pie2, StaticData.pieData2())
        pieChart2.data()
    }

    /**
     * Set legend in recycler view.
     */
    private fun setPieRecyclerView(){
        rvPieLegend?.layoutManager = GridLayoutManager(context,  2)
        rvPieLegend?.adapter = PieLegendAdapter(context, StaticData.pieData())

        rvPie2Legend?.layoutManager = GridLayoutManager(context,  2)
        rvPie2Legend?.adapter = PieLegendAdapter(context, StaticData.pieData2())
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
}