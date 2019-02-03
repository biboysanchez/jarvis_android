package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.R
import com.jarvis.app.adapter.PerformanceAndRiskAdapter
import com.jarvis.app.custom.CustomMarkerView
import com.jarvis.app.custom.LabelFormatter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Benchmark
import com.jarvis.app.model.RiskReturn
import com.jarvis.app.utils.ColorUtil
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_perfomance_measurement.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class PerformanceMeasurementFragment : BaseFragment(){
    private var arrayDanamasSaham:ArrayList<Benchmark>? = ArrayList()
    private var arraySimasSaham:ArrayList<Benchmark>? = ArrayList()
    private var arrayStackBar:ArrayList<RiskReturn> = ArrayList()

    private var sAdapter:PerformanceAndRiskAdapter? = null
    private var mAdapter:PerformanceAndRiskAdapter? = null

    companion object {
        val TAG = "PerformanceMeasurementFragment"
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_perfomance_measurement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPortfolioSpinner()
        getReturnBenchmarkData()
        setPerformanceAndRiskMeasurementTable()
    }

    private fun setPortfolioSpinner(){
        val arr = arrayListOf("All Holdings", "Danamas Saham", "Simas Saham Bertumbuh", "Bond C", "Bond D")
        spinnerPortfolio?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, arr)
        spinnerPortfolio?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //(parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#9E9E9E"))
                setStackBar()
            }
        }
    }

    private fun setStackBar() {
        val titleList = arrayListOf("2017", "2018")
        arrayStackBar = ArrayList()
        arrayStackBar.add(RiskReturn().get2017()[0])
        arrayStackBar.add(RiskReturn().get2017()[0])

        portfolioBarChart?.description?.isEnabled = false
        portfolioBarChart?.setMaxVisibleValueCount(1)
        portfolioBarChart?.setPinchZoom(false)
        portfolioBarChart?.setDrawGridBackground(false)
        portfolioBarChart?.setDrawBarShadow(false)
        portfolioBarChart?.setDrawValueAboveBar(false)
        portfolioBarChart?.axisRight?.isEnabled = false
        portfolioBarChart?.isHighlightFullBarEnabled = false
        portfolioBarChart?.legend?.isEnabled = false
        portfolioBarChart?.xAxis?.setDrawGridLines(false)
        portfolioBarChart?.axisLeft?.setDrawLabels(true)

        // change the position of the y-labels
        val leftAxis = portfolioBarChart?.axisLeft
        //leftAxis?.valueFormatter = MyValueFormatter("K")
        leftAxis?.axisMinimum = 0f // this replaces setStartAtZero(true)


        val xLabels = portfolioBarChart?.xAxis
        xLabels?.position = XAxis.XAxisPosition.TOP
        val values = ArrayList<BarEntry>()
        for (a in 0 until arrayStackBar.size){
            val r = arrayStackBar[a]
            values.add(BarEntry(a.toFloat(), floatArrayOf(r.corporateBonds, r.governmentBonds, r.equity, r.cash)))
        }

        val xAxis = portfolioBarChart?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = LabelFormatter(titleList)
        xAxis?.setLabelCount(titleList.size, false)

        val set1: BarDataSet
        if (portfolioBarChart?.data != null && portfolioBarChart.data!!.dataSetCount > 0) {
            set1 = portfolioBarChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            portfolioBarChart?.data!!.notifyDataChanged()
            portfolioBarChart?.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "")
            set1.setDrawIcons(false)
            set1.colors = ColorUtil.strategicAssetColors()
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueFormatter(StackedValueFormatter(false, "", 1))
            data.setValueTextColor(Color.WHITE)
            portfolioBarChart?.data = data
        }

        portfolioBarChart?.animateY(1300)
        portfolioBarChart?.setFitBars(true)
        portfolioBarChart?.invalidate()
    }

    private fun getReturnBenchmarkData(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.post(context!!, API.returnVsBenchmark, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrayDanamasSaham = ArrayList()
                        val json = JSONObject(response).obj("message_data").obj("portfolio_unique_dict")
                        val array1 = json.arr("Danamas Saham")
                        val array2 = json.arr("Simas Saham Bertumbuh")

                        for (i in 0 until array1.length()){
                            arrayDanamasSaham?.add(Benchmark(array1.getJSONObject(i)))
                        }

                        for (i in 0 until array2.length()){
                            arraySimasSaham?.add(Benchmark(array2.getJSONObject(i)))
                        }

                        setReturnBenchmarkSpinner()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setReturnBenchmarkSpinner(){
        spinnerSelection?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("Danamas Saham","Simas Saham Bertumbuh") )

        spinnerSelection?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#9E9E9E"))
                if (position == 0){
                    returnLineChart(arrayDanamasSaham!!)
                }else{
                    returnLineChart(arraySimasSaham!!)
                }
            }
        }
    }

    private fun returnLineChart(arrayList: ArrayList<Benchmark>){
        lineChartReturnBenchMark?.description = null
        lineChartReturnBenchMark?.setExtraOffsets(0f,0f,0f,20f)
        lineChartReturnBenchMark?.xAxis?.setDrawGridLines(false)
        lineChartReturnBenchMark?.axisLeft?.setDrawGridLines(true)
        lineChartReturnBenchMark?.axisRight?.setDrawLabels(false)
        lineChartReturnBenchMark?.legend?.isEnabled = false
        lineChartReturnBenchMark?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        lineChartReturnBenchMark?.setDrawMarkers(true)

        val mv = CustomMarkerView(context, R.layout.custom_marker_view_2)
        mv.chartView = lineChartReturnBenchMark // For bounds control
        lineChartReturnBenchMark?.marker = mv // Set the marker to the chart

        val tvTop = mv.findViewById<TextView>(R.id.tvTop)
        val tvSub = mv.findViewById<TextView>(R.id.tvBottom)

        lineChartReturnBenchMark?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val danamas = String.format("%.2f",arrayList[e?.x!!.toInt()].danamasSaham.toFloat() )
                val jci     = String.format("%.2f",arrayList[e.x.toInt()].jciIndex.toFloat() )
                tvSub.text = danamas
                tvTop.text = jci
            }
        })

        val dataSets = ArrayList<ILineDataSet>()
        val values = ArrayList<Entry>()
        val values1 = ArrayList<Entry>()
        var labels:ArrayList<String>? = ArrayList()

        for (z in 0 until 1) {
            labels = ArrayList()
            for (i in 0 until arrayList.size) {
                val benchmark = arrayList[i]
                values.add(Entry(i.toFloat(), benchmark.danamasSaham.toFloat()))
                values1.add(Entry(i.toFloat(), benchmark.jciIndex.toFloat()))
                labels.add(benchmark.id)
            }

            val d = LineDataSet(values, "Selected Portfolio")
            d.lineWidth = 2f
            d.setDrawCircles(false)
            d.setColors(Color.parseColor("#21C6B7"))
            dataSets.add(d)

            val e = LineDataSet(values1, "Benchmark")
            e.lineWidth = 2f
            e.setDrawCircles(false)
            e.setColors(Color.parseColor("#BDBDBD"))
            dataSets.add(e)
        }

        val xAxis = lineChartReturnBenchMark?.xAxis
        xAxis?.valueFormatter = ValueFormatter(labels)
        // xAxis?.granularity = 1f
        val data = LineData(dataSets)
        lineChartReturnBenchMark?.data = data

        val sets = lineChartReturnBenchMark.data.dataSets
        for (iSet in sets) {
            val set = iSet as LineDataSet
            set.setDrawValues(!set.isDrawValuesEnabled)
        }

        lineChartReturnBenchMark?.animateX(1600)
        lineChartReturnBenchMark?.invalidate()
    }

    private fun setPerformanceAndRiskMeasurementTable(){
        sAdapter = PerformanceAndRiskAdapter(context, true)
        mAdapter = PerformanceAndRiskAdapter(context, false)
        rvPortfolio1.layoutManager = LinearLayoutManager(context)
        rvPortfolio2.layoutManager = LinearLayoutManager(context)
        rvPortfolio1.adapter = sAdapter
        rvPortfolio2.adapter = mAdapter
    }
}