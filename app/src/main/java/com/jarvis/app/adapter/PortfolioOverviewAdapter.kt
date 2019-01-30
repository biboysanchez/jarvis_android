package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.PerformanceSummary
import kotlinx.android.synthetic.main.row_portfolio_overview.view.*

class PortfolioOverviewAdapter : RecyclerView.Adapter<PortfolioOverviewAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<PerformanceSummary>? = PerformanceSummary.getArrPerformanceSummary()
    private var isFirstColumn = false

    constructor(mContext: Context?, isShow:Boolean) : super() {
        this.mContext = mContext
        this.isFirstColumn = isShow
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PortfolioOverviewAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_portfolio_overview, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: PortfolioOverviewAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val summary = data!![i]

            if (isFirstColumn){
                itemView.tvRowPortfolio?.visibility = View.VISIBLE
                itemView.llRowPortfolio?.visibility = View.GONE
            }else{
                itemView.tvRowPortfolio?.visibility = View.GONE
                itemView.llRowPortfolio?.visibility = View.VISIBLE
            }

            val returnNav                   = "${summary.returnNav}%"
            val returnBenchmark             = "${summary.returnBenchmark}%"
            val mYield                      = "${summary.mYield}%"
            val mVar                        = "${summary.mVar}%"

            itemView.tvRowPortfolio?.text   = summary.portfolio
            itemView.tvRowAum?.text         = summary.aum.toString()
            itemView.tvRowNav?.text         = returnNav
            itemView.tvRowBenchmark?.text   = returnBenchmark
            itemView.tvRowIr?.text          = "${summary.ir}"
            itemView.tvRowYield?.text       = mYield
            itemView.tvRowVar?.text         = mVar

            if (i % 2 == 1){
                itemView.llRowPortfolioOverview?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowPortfolioOverview?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}