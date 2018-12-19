package com.jarvis.app.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ValueKey
import kotlinx.android.synthetic.main.row_progress.view.*


class CashFlowAdapter : RecyclerView.Adapter<CashFlowAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<ValueKey>? = ArrayList()

    constructor(mContext: Context?, data: List<ValueKey>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CashFlowAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_progress, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(p0: CashFlowAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.progressBar?.max = 10000
            itemView.progressBar?.progress = data!![i].value.toInt()
            itemView.tvProgressTitle?.text = data!![i].key
            itemView.tvProgressValue?.text = data!![i].value
            if (i % 2 == 1){
                itemView.progressBar?.progressDrawable = ContextCompat.getDrawable(mContext!!, R.drawable.progress_orange)
            }else{
                itemView.progressBar?.progressDrawable = ContextCompat.getDrawable(mContext!!, R.drawable.progress_primary)
            }
        }
    }
}