package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.model.GradientColor
import com.jarvis.app.R
import com.jarvis.app.custom.CustomMarkerView
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Benchmark
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_inflow_outflow.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class InflowOutflowAnalysisFragment : BaseFragment() {
    private var arrLineChart:ArrayList<Benchmark> = ArrayList()

    companion object {
        val TAG = "InflowOutflowAnalysisFragment"
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inflow_outflow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCombinedChart()
    }

    private fun setCombinedChart(){
        getLineChartData()
    }

    private fun getLineChartData(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.post(context!!, API.returnVsBenchmark, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrLineChart = ArrayList()
                        val json = JSONObject(response).obj("message_data").obj("portfolio_unique_dict")
                        val array1 = json.arr("Danamas Saham")

                        for (i in 0 until array1.length()){
                            arrLineChart.add(Benchmark(array1.getJSONObject(i)))
                        }
                       setLineChart()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun setLineChart() {
        combinedChart?.description = null
        combinedChart?.xAxis?.setDrawGridLines(false)
        combinedChart?.axisLeft?.setDrawGridLines(false)
        combinedChart?.axisLeft?.setDrawLabels(true)
        combinedChart?.axisRight?.setDrawLabels(false)
        combinedChart?.legend?.isEnabled = false
        combinedChart?.xAxis?.setDrawLabels(false)
        combinedChart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        combinedChart?.setDrawMarkers(true)
        val mv = CustomMarkerView(context, R.layout.custom_marker_view_2)
        mv.chartView = combinedChart // For bounds control
        combinedChart?.marker = mv // Set the marker to the chart
        val tvTop = mv.findViewById<TextView>(R.id.tvTop)
        val tvSub = mv.findViewById<TextView>(R.id.tvBottom)

        combinedChart?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val danamas = String.format("%.2f",arrLineChart[e?.x!!.toInt()].danamasSaham.toFloat() )
                val jci     = String.format("%.2f",arrLineChart[e.x.toInt()].jciIndex.toFloat() )
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
            for (i in 0 until 11) {
                val benchmark = arrLineChart[i]
                values.add(Entry(i.toFloat(), benchmark.danamasSaham.toFloat()))
                values1.add(Entry(i.toFloat(), benchmark.jciIndex.toFloat()))
                labels.add(benchmark.id)
            }

            val d = LineDataSet(values, "Redemption")
            d.lineWidth = 2f
            d.setDrawCircles(false)
            d.setColors(Color.parseColor("#E8781A"))
            dataSets.add(d)

            val e = LineDataSet(values1, "Subscription")
            e.lineWidth = 2f
            e.setDrawCircles(false)
            e.setColors(Color.parseColor("#BDBDBD"))
            dataSets.add(e)
        }

        val xAxis = combinedChart?.xAxis
        xAxis?.valueFormatter = ValueFormatter(labels)
        xAxis?.position = XAxis.XAxisPosition.BOTH_SIDED
        xAxis?.axisMinimum = 0f
        xAxis?.granularity = 1f
        val data = CombinedData()
        data.setData(setBarChart())

        val lineSet = LineData(dataSets)
        lineSet.setDrawValues(false)
        data.setData(lineSet)

        combinedChart?.data = data
        combinedChart?.animateX(1600)
        combinedChart?.invalidate()
    }
    private fun setBarChart() : BarData{
        val entries = ArrayList<BarEntry>()
        for (index in 0 until 11) {
            entries.add(BarEntry(index.toFloat(),  (Math.random() * 50).toFloat()))
        }
        val set1 = BarDataSet(entries, "Bar 1")
        set1.setDrawValues(false)
        set1.color = Color.parseColor("#21C6B7")
        return BarData(set1)
    }
}