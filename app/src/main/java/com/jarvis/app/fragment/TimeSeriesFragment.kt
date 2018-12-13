package com.jarvis.app.fragment

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.jarvis.app.R
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Benchmark
import com.jarvis.app.utils.JSONUtil
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_time_series.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.custom.MyMarkerView
import com.jarvis.app.helpers.ValueFormatter
import java.sql.Array


class TimeSeriesFragment : BaseFragment() {
    var benchmarkMap:HashMap<String, JSONArray>? = HashMap()
    var arrayDanamasSaham:ArrayList<Benchmark>? = ArrayList()
    var arraySimasSaham:ArrayList<Benchmark>? = ArrayList()

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "TimeSeriesFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setRecyclerAdapter()
        setSpinner()
        returnPortfolioListChart()
    }

    private fun setSpinner(){
        spinnerPerformance?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("JCI", "Saham", "Target") )

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

    private fun getData(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.returnVsBenchmark, params, object :ApiRequest.URLCallback{
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

                       setSpinner()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }


    private fun setRecyclerAdapter(){
        rvPerformanceAttribute?.layoutManager = LinearLayoutManager(context)
     //   rvPerformanceAttribute?.adapter = PerformanceSummaryAdapter(context, null)
    }

    private fun returnLineChart(arrayList: ArrayList<Benchmark>){
        lineChartReturnBenchMark?.description = null
        lineChartReturnBenchMark?.setExtraOffsets(0f,0f,0f,20f)
        lineChartReturnBenchMark?.xAxis?.setDrawGridLines(false)
        lineChartReturnBenchMark?.axisLeft?.setDrawGridLines(true)
        lineChartReturnBenchMark?.axisRight?.setDrawLabels(false)
        lineChartReturnBenchMark?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        lineChartReturnBenchMark?.setDrawMarkers(true)


        val mv = MyMarkerView(context, R.layout.custom_marker_view)
        mv.chartView = lineChartReturnBenchMark // For bounds control
        lineChartReturnBenchMark?.marker = mv // Set the marker to the chart
        lineChartReturnBenchMark?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Log.i("Entry selected", e.toString())
                Log.i("LOW HIGH", "low: " + lineChartReturnBenchMark?.lowestVisibleX + ", high: " + lineChartReturnBenchMark?.highestVisibleX)
                Log.i("MIN MAX", "xMin: " + lineChartReturnBenchMark?.xChartMin + ", xMax: " + lineChartReturnBenchMark?.xChartMax + ", yMin: " + lineChartReturnBenchMark?.yChartMin + ", yMax: " + lineChartReturnBenchMark?.yChartMax
                )
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

            val d = LineDataSet(values, "Sanamas Saham")
            d.lineWidth = 2f
            d.setDrawCircles(false)
            d.setColors(Color.parseColor("#21C6B7"))
            dataSets.add(d)

            val e = LineDataSet(values1, "Jci Index")
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
        lineChartReturnBenchMark?.invalidate()
    }

    private fun returnPortfolioListChart(){
        val entries = ArrayList<Entry>()
        entries.add(Entry(10f, 80f))
        entries.add(Entry(20f, 85f))
        entries.add(Entry(30f, 80f))
        entries.add(Entry(40f, 86f))
        entries.add(Entry(50f, 81f))
        entries.add(Entry(60f, 87f))

        val entries2 = ArrayList<Entry>()
        entries2.add(Entry(10f, 75f))
        entries2.add(Entry(20f, 74f))
        entries2.add(Entry(30f, 72f))
        entries2.add(Entry(40f, 78f))
        entries2.add(Entry(50f, 74f))
        entries2.add(Entry(60f, 78f))

        val entries3 = ArrayList<Entry>()
        entries3.add(Entry(10f, 65f))
        entries3.add(Entry(20f, 66f))
        entries3.add(Entry(30f, 62f))
        entries3.add(Entry(40f, 64f))
        entries3.add(Entry(50f, 58f))
        entries3.add(Entry(60f, 67f))

        // sort by x-value
        Collections.sort(entries, EntryXComparator())

        // create a dataset and give it a type
        val set1 = LineDataSet(entries, "DataSet 1")
        set1.lineWidth = 0f
        set1.setDrawCircles(false)
        set1.setDrawFilled(true)
        set1.valueTextColor = android.R.color.transparent
        set1.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl1)

        // create a dataset and give it a type
        val set2 = LineDataSet(entries2, "DataSet 2")
        set2.lineWidth = 0f
        set2.setDrawCircles(false)
        set2.setDrawFilled(true)
        set2.valueTextColor = android.R.color.transparent
        set2.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl2)

        // create a dataset and give it a type
        val set3 = LineDataSet(entries3, "DataSet 3")
        set3.lineWidth = 0f
        set3.setDrawCircles(false)
        set3.setDrawFilled(true)
        set3.valueTextColor = android.R.color.transparent
        set3.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.solid_green_lvl3)

        // create a data object with the data sets
        val data = LineData(set1, set2, set3)

        portfolioLineChart?.legend?.isEnabled = false
        portfolioLineChart?.description = null
        portfolioLineChart?.axisRight?.setDrawLabels(false)
        portfolioLineChart?.setDrawGridBackground(true)
        portfolioLineChart?.setGridBackgroundColor(Color.parseColor("#2C7B74"))


        // set data
        portfolioLineChart?.data = data
        portfolioLineChart?.invalidate()
    }
}