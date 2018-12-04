package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.Pie
import kotlinx.android.synthetic.main.row_pie_legend.view.*
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.graphics.Typeface
import android.text.style.StyleSpan




class PieLegendAdapter : RecyclerView.Adapter<PieLegendAdapter.ViewHolder> {
    private var mContext:Context? = null
    private var data:List<Pie>? = ArrayList()

    constructor(mContext: Context?, data: List<Pie>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PieLegendAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_pie_legend, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(p0: PieLegendAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bindItem(i:Int){
            val pie = data?.get(i)
            val label = "${pie?.name}\n[${pie?.percent}]% ${pie?.amount} B"

            val ssBuilder = SpannableStringBuilder(label)
            val boldSpan  = StyleSpan(Typeface.BOLD)

            ssBuilder.setSpan(
                boldSpan,
                pie?.name!!.indexOf(pie.name),
                pie.name.indexOf(pie.name) + pie.name.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            itemView.viewLegend?.setBackgroundColor(pie.color)
            itemView.tvLegendName?.text = ssBuilder
        }
    }
}