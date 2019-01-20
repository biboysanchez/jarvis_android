package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.row_6_column.view.*

class RelativeValuationAdapter : RecyclerView.Adapter<RelativeValuationAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var isHeader = false

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    fun isHeader(isShow:Boolean){
        this.isHeader = isShow
    }

 /*   fun addItem(comparation: Comparation) {
        notifyItemInserted(itemCount - 1)
        notifyItemRangeInserted(itemCount - 1, itemCount)
    }*/

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RelativeValuationAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_6_column, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(p0: RelativeValuationAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            if (isHeader){
                itemView.tvRowColumn0?.visibility = View.GONE
                itemView.ll3Row?.visibility = View.VISIBLE
            }else{
                itemView.tvRowColumn0?.visibility = View.VISIBLE
                itemView.ll3Row?.visibility = View.GONE
            }

            if (i % 2 == 1){
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}