package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.dataholder.StaticData
import kotlinx.android.synthetic.main.row_balance_sheet_asset.view.*
import kotlinx.android.synthetic.main.row_progress_negative.view.*

class IncomeStatementAdapter : RecyclerView.Adapter<IncomeStatementAdapter.ViewHolder> {
    private var mContext:Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IncomeStatementAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_progress_negative, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return StaticData.incomeStatements().size
    }

    override fun onBindViewHolder(p0: IncomeStatementAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bindItem(i:Int){
            val data = StaticData.incomeStatements()[i]
            val portfolio = "${data.portfolio} (%)"
            itemView.tvProgressNegativeTitle.text = portfolio

            if (data.cost <= 0){
                val cost = (data.cost * -1 * 100).toInt()

                itemView.progressBarNegative?.max = 15000
                itemView.progressBarPositive?.progress = 0
                itemView.progressBarNegative?.progress = cost
                itemView.tvProgressNegativeValue?.text = data.cost.toInt().toString()
                itemView.tvProgressNegativeValue?.visibility = View.VISIBLE
                itemView.tvProgressPositiveValue?.visibility = View.GONE
            }else{
                itemView.progressBarPositive?.max = 150
                itemView.progressBarPositive?.progress = data.cost.toInt()
                itemView.progressBarNegative?.progress = 0
                itemView.tvProgressPositiveValue?.text = data.cost.toInt().toString()
                itemView.tvProgressPositiveValue?.visibility = View.VISIBLE
                itemView.tvProgressNegativeValue?.visibility = View.GONE
            }
        }
    }
}