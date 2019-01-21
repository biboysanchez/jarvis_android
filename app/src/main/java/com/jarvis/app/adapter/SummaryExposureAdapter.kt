package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.SummaryExposure
import kotlinx.android.synthetic.main.row_4_column.view.*
class SummaryExposureAdapter : RecyclerView.Adapter<SummaryExposureAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data:List<SummaryExposure> = SummaryExposure.getExposure()
    private var isHeader = false

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    fun isHeader(isShow:Boolean){
        this.isHeader = isShow
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_4_column, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int) {
            val exposure = data[position]
            itemView.tvRowColumn0?.text = exposure.company
            itemView.tvRowColumn1?.text = exposure.revenue
            itemView.tvRowColumn2?.text = exposure.income
            itemView.tvRowColumn3?.text = exposure.net

            if (isHeader){
                itemView.tvRowColumn0?.visibility = View.GONE
                itemView.ll3Row?.visibility = View.VISIBLE
            }else{
                itemView.tvRowColumn0?.visibility = View.VISIBLE
                itemView.ll3Row?.visibility = View.GONE
            }

            if (position % 2 == 1){
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.TRANSPARENT)
            }else{
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#EEF4F3"))

            }
        }
    }
}