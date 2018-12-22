package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ValueKey
import kotlinx.android.synthetic.main.row_duration.view.*
import kotlinx.android.synthetic.main.row_home_list.view.*

class CashSummaryAdapter : RecyclerView.Adapter<CashSummaryAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<ValueKey>? = ArrayList()

    constructor(mContext: Context?, data: List<ValueKey>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    fun sortCashSummary(sorter: Int) {
        when (sorter) {
            0 -> {
                data = data?.sortedWith(compareBy {it.key})
            }

            1 -> {
                data = data?.sortedByDescending { it.key }
            }

            2 -> {
                data?.sortedByDescending { it.value }
            }

            else -> {
                data?.sortedWith(compareBy { it.value })
            }
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CashSummaryAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_duration, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: CashSummaryAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val asset = data?.get(i)
            val value = asset?.value?.toFloat()
            itemView.tvRowAssetName?.text = asset?.key
            itemView.tvRowAssetValue?.text = value.toString()

            if (i % 2 == 1){
                itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}