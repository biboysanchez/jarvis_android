package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.button_scroll.view.*

class HorizontalListAdapter : RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<String>? = ArrayList()
    private var lastIndex = 100

    constructor(mContext: Context?, data: List<String>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HorizontalListAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.button_scroll, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: HorizontalListAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.tvBtnText?.text = data?.get(i)

//            if (itemView.tvBtnText?.tag == i){
//                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_light)
//            }else{
//                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_primary)
//            }

            if (lastIndex == i){
                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_primary)
            }

            itemView.tvBtnText?.setOnClickListener {
                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_light)
                notifyDataSetChanged()
                lastIndex = i
            }
        }

    }
}