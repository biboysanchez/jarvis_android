package com.jarvis.app.fragment

import android.graphics.Color
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
import android.widget.Toast
import com.android.volley.VolleyError
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jarvis.app.R
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Benchmark
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_time_series.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.custom.CustomMarkerView
import com.jarvis.app.extension.string
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API.portfolio
import com.jarvis.app.model.Portfolio
import com.jarvis.app.utils.ColorUtil
import com.jarvis.app.utils.Util
import kotlin.Comparator


class TimeSeriesFragment : BaseFragment() {
    var arrayDanamasSaham:ArrayList<Benchmark>? = ArrayList()
    var arraySimasSaham:ArrayList<Benchmark>? = ArrayList()
    var mapPortfolio:HashMap<String, ArrayList<Portfolio>>? = HashMap()
    var keys:ArrayList<String>?= ArrayList()
    var arrPortfolioTitle:ArrayList<String> = ArrayList()

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "TimeSeriesFragment"
        var instance:TimeSeriesFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_time_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setRecyclerAdapter()
        setSpinner()

        getPortfolio()
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

    fun refreshAll(){
        getData()
        getPortfolio()
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

        val mv = CustomMarkerView(context, R.layout.custom_marker_view_2)
        mv.chartView = lineChartReturnBenchMark // For bounds control
        lineChartReturnBenchMark?.marker = mv // Set the marker to the chart

        val tvTop = mv.findViewById<TextView>(R.id.tvTop)
        val tvSub = mv.findViewById<TextView>(R.id.tvBottom)

        lineChartReturnBenchMark?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
               //Log.i("Entry selected", e.toString())
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

            val d = LineDataSet(values, "Danamas Saham")
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

        lineChartReturnBenchMark?.animateX(1600)
        lineChartReturnBenchMark?.invalidate()
    }

    private fun getPortfolio(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["portfolio"] = "Danamas Saham"
        ApiRequest.postNoUI(context!!, API.portfolio, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        mapPortfolio = HashMap()
                        keys         = ArrayList()
                        val json     = JSONObject(response).obj("message_data").obj("portfolio_unique_dict")
                        val iter: Iterator<String> = json.keys()
                        while (iter.hasNext()) {
                            val key = iter.next()
                            try {
                                keys?.add(key)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                        var data: LineData? = null
                        val arrSet:ArrayList<LineDataSet>? = ArrayList()

                        for (i in 0 until keys!!.size){
                            val key = keys!![i]
                            val arr = json.getJSONArray(key)
                            var set: LineDataSet? = null

                            val arrayList = ArrayList<Portfolio>()
                            val entries = ArrayList<Entry>()
                            for (a in 0 until arr.length()) {
                                val portfolio = Portfolio(arr.getJSONObject(a))
                                portfolio.dateLong = Util.getDateMillisFromString(portfolio.date.toString())
                                arrayList.add(portfolio)
                               arrayList.sortWith(Comparator { o1, o2 -> o1?.dateLong!!.compareTo(o2!!.dateLong) })
                            }

                            arrayList.sortWith(Comparator { o1, o2 -> o1?.dateLong!!.compareTo(o2!!.dateLong) })
                            arrPortfolioTitle = ArrayList()
                            for (a in 0 until arrayList.size) {
                                val portfolio = arrayList[a]
                                Log.i(TAG, "date:::>($a) ${portfolio.date}")
                                if (!arrPortfolioTitle.contains(portfolio.date)){
                                    arrPortfolioTitle.add(portfolio.date)
                                }
                                entries.add(Entry(a.toFloat(), portfolio.target.replace("%", "").toFloat()))
                            }

                            set = LineDataSet(entries, key)
                            set.color = ColorUtil.arrColorA()[i]
                            set.lineWidth = 1f
                            set.setDrawCircles(false)
                            set.setDrawFilled(true)
                            set.valueTextColor = android.R.color.transparent
                            set.fillColor = ColorUtil.arrColorA()[i]
                            arrSet?.add(set)
                        }

                        val xAxis = portfolioLineChart?.xAxis
                        xAxis?.valueFormatter = ValueFormatter(arrPortfolioTitle)

                        data = LineData(arrSet as List<ILineDataSet>?)
                        portfolioLineChart?.setExtraOffsets(0f,0f,0f,20f)
                        portfolioLineChart?.xAxis?.setDrawGridLines(false)
                        portfolioLineChart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
                        portfolioLineChart?.animateX(1600)
                        portfolioLineChart?.legend?.isEnabled = true
                        portfolioLineChart?.description = null
                        portfolioLineChart?.axisRight?.setDrawLabels(false)
                        portfolioLineChart?.setDrawGridBackground(true)
                        portfolioLineChart?.setGridBackgroundColor(Color.parseColor("#F4F9F9"))
                        portfolioLineChart?.data = data
                        portfolioLineChart?.invalidate()
                    }catch (e: JSONException){
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