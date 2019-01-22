package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.LegendColor
import kotlinx.android.synthetic.main.row_pie_legend.view.*

class SimpleColorLabelAdapter : RecyclerView.Adapter<SimpleColorLabelAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<LegendColor>? = LegendColor.legend()

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleColorLabelAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_pie_legend, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: SimpleColorLabelAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.tvLegendName?.text = data!![i].label
            itemView.viewLegend?.setBackgroundColor(data!![i].color)
        }
    }
}