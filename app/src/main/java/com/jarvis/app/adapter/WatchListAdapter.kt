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


class WatchListAdapter : RecyclerView.Adapter<WatchListAdapter.ViewHolder> {
    private var mContext: Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WatchListAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_home_list, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(p0: WatchListAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.tvRowTable1Name?.text           = "ADRO"
            itemView.tvRowTable1SubTitle?.text       = "Stocks"
            itemView.tvRowTable1Value?.text          = "12,093"
            itemView.tvRowTable1SubTitle?.visibility = View.VISIBLE

            if (i % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}