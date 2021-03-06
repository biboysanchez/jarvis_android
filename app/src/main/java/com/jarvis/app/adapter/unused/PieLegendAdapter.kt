package com.jarvis.app.adapter.unused

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.row_pie_legend.view.*
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.graphics.Typeface
import android.text.style.StyleSpan
import com.jarvis.app.R.id.tvLegendName
import com.jarvis.app.model.PieModel
import com.jarvis.app.utils.Util


class PieLegendAdapter : RecyclerView.Adapter<PieLegendAdapter.ViewHolder> {
    private var mContext:Context? = null
    private var data:List<PieModel>? = ArrayList()

    constructor(mContext: Context?, data: List<PieModel>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_pie_legend, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bindItem(i:Int){
            val pie = data?.get(i)
            val mPercent = "${pie?.percentage}%"
            val amount = "${Util.priceFormat(pie?.portofolio!!.toFloat()).replace(".00", "")} B"
            itemView.viewLegend?.setBackgroundColor(pie.color)
            itemView.tvLegendName?.text = pie.item
            itemView.tvLegendPercent?.text = mPercent
            itemView.tvLegendValue?.text = amount

            if (i % 2 == 1){
                itemView.llRowChartLegend?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowChartLegend?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}