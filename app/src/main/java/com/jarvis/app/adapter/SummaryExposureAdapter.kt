package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.SummaryExposure
import kotlinx.android.synthetic.main.row_summary_exposure.view.*


class SummaryExposureAdapter : RecyclerView.Adapter<SummaryExposureAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data:List<SummaryExposure> = SummaryExposure.getExposure()
    private var isAll = false

    constructor(mContext: Context?, isAll:Boolean) : super() {
        this.mContext = mContext
        this.isAll = isAll
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_summary_exposure, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isAll){
            data.size
        }else
            5
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int) {
            val exposure = data[position]
            itemView.rowTvSummaryCompany?.text      = exposure.company
            itemView.rowTvSummarySubtitle?.text     = exposure.industry
            itemView.rowTvSummaryExposure?.text     = exposure.exposure
            itemView.rowTvSummarySensitivity?.text  = exposure.netSensitivity

            if (position % 2 == 1){
               // itemView.llBgExposure?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                itemView.llBgExposure?.setBackgroundColor(Color.TRANSPARENT)
            }else{
                itemView.llBgExposure?.setBackgroundColor(Color.parseColor("#EEF4F3"))

            }
        }
    }
}