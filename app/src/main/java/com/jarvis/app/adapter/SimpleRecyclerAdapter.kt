package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.simple_text1.view.*

class SimpleRecyclerAdapter : RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data = ArrayList<String>()

    constructor(mContext: Context?, data: ArrayList<String>) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.simple_text1, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: SimpleRecyclerAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.tvPlainText?.text = data[i]
        }
    }
}