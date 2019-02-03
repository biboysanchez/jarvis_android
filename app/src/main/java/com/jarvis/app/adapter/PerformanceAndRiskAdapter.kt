package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.PerformanceMeasurement
import kotlinx.android.synthetic.main.row_performance_risk.view.*
class PerformanceAndRiskAdapter : RecyclerView.Adapter<PerformanceAndRiskAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<PerformanceMeasurement>? = PerformanceMeasurement.arrPerformanceMeasurements()
    private var isFirstColumn = false

    constructor(mContext: Context?, isShow:Boolean) : super() {
        this.mContext = mContext
        this.isFirstColumn = isShow
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PerformanceAndRiskAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_performance_risk, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: PerformanceAndRiskAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val summary = data!![i]

            if (isFirstColumn){
                itemView.tvRowPortPerformance?.visibility = View.VISIBLE
                itemView.llRowPortPerformance?.visibility = View.GONE
            }else{
                itemView.tvRowPortPerformance?.visibility = View.GONE
                itemView.llRowPortPerformance?.visibility = View.VISIBLE
            }


            itemView.tvRowPortPerformance?.text   = summary.portfolio
            itemView.tvRowAum?.text               = summary.aum
            itemView.tvRowBenchmark?.text         = summary.benchmark
            itemView.tvRowYtd?.text               = summary.ytdAr
            itemView.tvRowTarget?.text            = summary.targetAr


            if (i % 2 == 1){
                itemView.llRowPortfolioPerformance?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowPortfolioPerformance?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}