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
import java.util.*

class TopicAdapter : RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<ValueKey>? = Arrays.asList(
        ValueKey("Acquisition", "60"),
        ValueKey("Divestitures", "58"),
        ValueKey("Merger", "49"),
        ValueKey("Spin off", "65")
    )

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TopicAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_duration, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: TopicAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val topic = data?.get(i)
            itemView.tvRowAssetName?.text = topic?.key
            itemView.tvRowAssetValue?.text = topic?.value

            if (i % 2 == 1){
                itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}