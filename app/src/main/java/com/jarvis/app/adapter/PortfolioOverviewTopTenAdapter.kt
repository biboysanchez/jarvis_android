package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.TopTen
import kotlinx.android.synthetic.main.row_top_10_position.view.*

class PortfolioOverviewTopTenAdapter : RecyclerView.Adapter<PortfolioOverviewTopTenAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<TopTen>? = TopTen.arrTopTen()
    private var isFirstColumn = false

    constructor(mContext: Context?, isShow:Boolean) : super() {
        this.mContext = mContext
        this.isFirstColumn = isShow
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PortfolioOverviewTopTenAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_top_10_position, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: PortfolioOverviewTopTenAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val topTen = data!![i]

            if (isFirstColumn){
                itemView.tvRowTopTenPortfolio?.visibility = View.VISIBLE
                itemView.llRowTopTen?.visibility = View.GONE
            }else{
                itemView.tvRowTopTenPortfolio?.visibility = View.GONE
                itemView.llRowTopTen?.visibility = View.VISIBLE
            }

            itemView.tvRowTopTenPortfolio?.text   = topTen.security
            itemView.tvRowNatlValue?.text         = topTen.natlValue.toString()
            itemView.tvRowUnrealized?.text        = topTen.unrealized.toString()
            itemView.tvRowAveCost?.text           = topTen.avgCost.toString()
            itemView.tvRowCurrentPrice?.text      = topTen.currentPrice.toString()

            if (i % 2 == 1){
                itemView.llRowTopTenOverview?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowTopTenOverview?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}