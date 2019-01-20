package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.FinancialHighlight
import kotlinx.android.synthetic.main.row_3_column.view.*

class FinancialHighlightAdapter : RecyclerView.Adapter<FinancialHighlightAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<FinancialHighlight>? = FinancialHighlight.getFinancialHightlights()

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FinancialHighlightAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_3_column, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: FinancialHighlightAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val financial = data!![i]
            itemView.tvRowColumn1?.text = financial.year
            itemView.tvRowColumn2?.text = financial.netSale
            itemView.tvRowColumn3?.text = financial.cogs

            if (i % 2 == 1){
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}