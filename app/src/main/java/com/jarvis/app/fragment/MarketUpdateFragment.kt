package com.jarvis.app.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.VolleyError
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jarvis.app.R
import com.jarvis.app.custom.CustomMarkerView
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.helpers.ValueFormatter
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.Benchmark
import com.jarvis.app.model.RiskReturn
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.paging_market_update_line_chart.view.*
import kotlinx.android.synthetic.main.row_market_update.view.*
import org.json.JSONException
import org.json.JSONObject

class MarketUpdateFragment: BaseFragment() {
    var viewPagerAdapter: InnerViewPagerAdapter?  = null
    var viewpagerTitles = java.util.ArrayList<String>()
    var arrLineChart:ArrayList<Benchmark> = ArrayList()

    val arrObservation = arrayListOf(
        "Indonesian Rupiah is one of the most performer in the region in the last month.",
        "THis recovery should provide significant relief for companies with high USD exposure, such as Garuda, Alam Sutera, and Telkom.",
        "IDR recovery usually impact equity prices more than bonds, as the former tends to be more elastic."
    )

    val arrRecommendations = arrayListOf(
        "Keep-in-view infrastructure stocks with USD liabilities position for buying opportunities (e.g. TLKM, WIKA), while also closely track ASRI USD Bonds for exit opportunity if price recovers"
    )

    companion object {
        val TAG = "MarketUpdateFragment"
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setRecyclerView()
    }

    private fun setViewPager(){
        viewPagerAdapter = InnerViewPagerAdapter(context!!)
        viewPagerAdapter?.addItem(RiskReturn().getExpectedReturn())
        viewPagerAdapter?.addItem(RiskReturn().getExpectedRisk())
        getLineChartData()
    }

    private fun syncViewPager(){
        viewPagerMarketUpdate.offscreenPageLimit = 4
        pageIndicatorView?.count = 4
        viewPagerMarketUpdate?.adapter = viewPagerAdapter
    }

    private fun setRecyclerView(){
        rvObservation?.layoutManager = LinearLayoutManager(context)
        rvRecommendation?.layoutManager = LinearLayoutManager(context)
        rvObservation.adapter = MarketUpdateRecyclerAdapter(context, arrObservation)
        rvRecommendation.adapter = MarketUpdateRecyclerAdapter(context, arrRecommendations)
    }

    private fun getLineChartData(){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        ApiRequest.postNoUI(context!!, API.returnVsBenchmark, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrLineChart = ArrayList()
                        val json = JSONObject(response).obj("message_data").obj("portfolio_unique_dict")
                        val array1 = json.arr("Danamas Saham")

                        for (i in 0 until array1.length()){
                            arrLineChart.add(Benchmark(array1.getJSONObject(i)))
                        }

                        syncViewPager()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    inner class  MarketUpdateRecyclerAdapter: RecyclerView.Adapter<MarketUpdateRecyclerAdapter.ViewHolder> {
        var mContext: Context? = null
        var data:List<String> = ArrayList()

        constructor(mContext: Context?, data:List<String>) : super() {
            this.mContext = mContext
            this.data = data
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MarketUpdateRecyclerAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_market_update, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                itemView.tvText?.text = data[i]
            }
        }
    }

    inner class InnerViewPagerAdapter(private val mContext: Context) : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val itemVIew = inflater.inflate(R.layout.paging_market_update_line_chart, collection, false) as ViewGroup
            setLineChart(itemVIew, position)

            collection.addView(itemVIew)
            return itemVIew
        }

        fun addItem(arr: ArrayList<RiskReturn>){
           // viewPagerArray.add(arr)
            notifyDataSetChanged()
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return viewpagerTitles[position]
        }

        private fun setLineChart(itemVIew: ViewGroup, position: Int) {
            itemVIew.marketLineChart?.description = null
            itemVIew.marketLineChart?.xAxis?.setDrawGridLines(false)
            itemVIew.marketLineChart?.axisLeft?.setDrawGridLines(false)
            itemVIew.marketLineChart?.axisLeft?.setDrawLabels(false)
            itemVIew.marketLineChart?.axisRight?.setDrawLabels(true)
            itemVIew.marketLineChart?.legend?.isEnabled = false
            itemVIew.marketLineChart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
            itemVIew.marketLineChart?.setDrawMarkers(true)

            val mv = CustomMarkerView(context, R.layout.custom_marker_view_2)
            mv.chartView = itemVIew.marketLineChart // For bounds control
            itemVIew.marketLineChart?.marker = mv // Set the marker to the chart

            val tvTop = mv.findViewById<TextView>(R.id.tvTop)
            val tvSub = mv.findViewById<TextView>(R.id.tvBottom)

            itemVIew.marketLineChart?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
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
                for (i in 0 until arrLineChart.size) {
                    val benchmark = arrLineChart[i]
                    values.add(Entry(i.toFloat(), benchmark.danamasSaham.toFloat()))
                    values1.add(Entry(i.toFloat(), benchmark.jciIndex.toFloat()))
                    labels.add(benchmark.id)
                }

                val d = LineDataSet(values, "Sun 10Y Yield")
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

            val xAxis = itemVIew.marketLineChart?.xAxis
            xAxis?.valueFormatter = ValueFormatter(labels)
            val data = LineData(dataSets)
            itemVIew.marketLineChart?.data = data

            val sets = itemVIew.marketLineChart.data.dataSets
            for (iSet in sets) {
                val set = iSet as LineDataSet
                set.setDrawValues(!set.isDrawValuesEnabled)
            }

            itemVIew.marketLineChart?.animateX(1600)
            itemVIew.marketLineChart?.invalidate()
        }
    }
}