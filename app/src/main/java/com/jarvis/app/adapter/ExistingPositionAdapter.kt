package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ExistingPosition
import com.jarvis.app.model.FinancialHighlight
import kotlinx.android.synthetic.main.row_3_column.view.*

class ExistingPositionAdapter : RecyclerView.Adapter<ExistingPositionAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<ExistingPosition>? = ExistingPosition.getExistingPosition()

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ExistingPositionAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_3_column, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: ExistingPositionAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val financial = data!![i]
            itemView.tvRowColumn1?.text = financial.name
            itemView.tvRowColumn2?.text = financial.costPrice
            itemView.tvRowColumn3?.text = financial.marketPrice

            if (i % 2 == 1){
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}