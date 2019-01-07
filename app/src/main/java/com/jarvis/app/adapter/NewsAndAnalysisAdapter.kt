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
import kotlinx.android.synthetic.main.row_analysis.view.*
import kotlinx.android.synthetic.main.row_home_list.view.*
import kotlinx.android.synthetic.main.row_progress.view.*


class NewsAndAnalysisAdapter : RecyclerView.Adapter<NewsAndAnalysisAdapter.ViewHolder> {
    private var mContext: Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsAndAnalysisAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_analysis, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(p0: NewsAndAnalysisAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {

            if (i % 2 == 1){
                itemView.llRowLayout?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                itemView.tvRowAnalysis?.text = "Analis: Saham Uniliver Indonesia (UNVR) menaarik dilirik hingga tahun depan"
            }else{
                itemView.llRowLayout?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                itemView.tvRowAnalysis?.text = "Emiten Kebun Agresif Taman Pabrik, Menilik Laju UNVR"
            }
        }
    }
}