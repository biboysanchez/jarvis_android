package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.row_comparator_company.view.*

class DialogMetricsAdapter : RecyclerView.Adapter<DialogMetricsAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data:List<String> = ArrayList()

    constructor(mContext: Context?, arr:List<String>) : super() {
        this.mContext = mContext
        this.data = arr
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DialogMetricsAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_comparator_company, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: DialogMetricsAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            itemView.cbCompany?.text = data[i]
            itemView.cbCompany?.setOnCheckedChangeListener { buttonView, isChecked ->
            }

            if (i % 2 == 1){
                itemView.llRowComparationCompany?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowComparationCompany?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}