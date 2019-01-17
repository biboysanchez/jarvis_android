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
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.adapter.ComparationAdapter
import com.jarvis.app.custom.CustomMarkerView
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.fragment_equities.*
import kotlinx.android.synthetic.main.fragment_equities.view.*
import kotlinx.android.synthetic.main.row_company_industry.view.*
import kotlinx.android.synthetic.main.simple_text.view.*

class EquitiesFragment : BaseFragment() {
    private var arrayDanamasSaham:ArrayList<Benchmark>? = ArrayList()
    private var arraySimasSaham:ArrayList<Benchmark>? = ArrayList()
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "EquitiesFragment"
        var instance: EquitiesFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_equities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setSpinner()
        setComparation()
    }

    private fun setComparation(){
        for (i in 0 until Comparation.getComparation().size){
            val comparation = Comparation.getComparation()[i]

            val mView0 = LayoutInflater.from(context).inflate(R.layout.row_company_industry, null)
            mView0.tvRowCompany?.text = comparation.company
            mView0.tvRowIndustry?.text = comparation.industry
            mView0.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                144
            )

            val mView1 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
            mView1.tvSimpleText?.text = comparation.lorem
            mView1.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                144
            )

            val mView2 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
            mView2.tvSimpleText?.text = comparation.ipsum
            mView2.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                144
            )

            val mView3 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
            mView3.tvSimpleText?.text = comparation.dolor
            mView3.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                144
            )

            llCol0.addView(mView0)
            llCol1.addView(mView1)
            llCol2.addView(mView2)
            llCol3.addView(mView3)

            if (i % 2 == 1){
                mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }

    private fun setSpinner(){
        spinnerEquities?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("Danamas Saham","Simas Saham Bertumbuh") )

        spinnerEquities?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

    private fun returnLineChart(arrayList: ArrayList<Benchmark>){
        lineChartEquities?.description = null
        lineChartEquities?.setExtraOffsets(0f,0f,0f,20f)
        lineChartEquities?.xAxis?.setDrawGridLines(false)
        lineChartEquities?.axisLeft?.setDrawGridLines(true)
        lineChartEquities?.axisRight?.setDrawLabels(false)
        lineChartEquities?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        lineChartEquities?.setDrawMarkers(true)

        val mv = CustomMarkerView(context, R.layout.custom_marker_view_2)
        mv.chartView = lineChartEquities // For bounds control
        lineChartEquities?.marker = mv // Set the marker to the chart

        val tvTop = mv.findViewById<TextView>(R.id.tvTop)
        val tvSub = mv.findViewById<TextView>(R.id.tvBottom)

        lineChartEquities?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
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

            val d = LineDataSet(values, "Selected Company")
            d.lineWidth = 2f
            d.setDrawCircles(false)
            d.setColors(Color.parseColor("#21C6B7"))
            dataSets.add(d)

            val e = LineDataSet(values1, "Index")
            e.lineWidth = 2f
            e.setDrawCircles(false)
            e.setColors(Color.parseColor("#BDBDBD"))
            dataSets.add(e)
        }

        val xAxis = lineChartEquities?.xAxis
        xAxis?.valueFormatter = ValueFormatter(labels)
       // xAxis?.granularity = 1f
        val data = LineData(dataSets)
        lineChartEquities?.data = data

        val sets = lineChartEquities.data.dataSets
        for (iSet in sets) {
            val set = iSet as LineDataSet
            set.setDrawValues(!set.isDrawValuesEnabled)
        }

        lineChartEquities?.animateX(1600)
        lineChartEquities?.invalidate()
    }
    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}