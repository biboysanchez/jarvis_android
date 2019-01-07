package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ValueKey
import kotlinx.android.synthetic.main.row_home_list.view.*
import kotlinx.android.synthetic.main.row_progress.view.*


class SummaryParamAdapter : RecyclerView.Adapter<SummaryParamAdapter.ViewHolder> {
    private var mContext: Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SummaryParamAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_home_list, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(p0: SummaryParamAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val tempVal  = 2013 + i
            itemView.tvRowTable1Name?.text           = "$tempVal"
            itemView.tvRowTable1Value?.text          = "21.$i" + "00"

            if (i % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}