package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.fragment.CompanyDetailFragment
import com.jarvis.app.fragment.EquitiesFragment
import com.jarvis.app.fragment.FixedIncomeFragment
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.row_4_column.view.*
import kotlinx.android.synthetic.main.row_comparition.view.*

class SummaryFinancialAdapter : RecyclerView.Adapter<SummaryFinancialAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Comparation>? = ArrayList()
    private var isHeader = false

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    fun isHeader(isShow:Boolean){
        this.isHeader = isShow
    }

    fun addItem(comparation: Comparation){
        data?.add(comparation)
        notifyItemInserted(itemCount-1)
        notifyItemRangeInserted(itemCount-1, itemCount)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SummaryFinancialAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_4_column, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: SummaryFinancialAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val comparation = data!![i]
            itemView.tvRowColumn1?.text = comparation.lorem
            itemView.tvRowColumn2?.text = comparation.ipsum
            itemView.tvRowColumn3?.text = comparation.dolor
            itemView.tvRowColumn0?.text = comparation.company

            if (isHeader){
                itemView.tvRowColumn0?.visibility = View.GONE
                itemView.ll3Row?.visibility = View.VISIBLE
            }else{
                itemView.tvRowColumn0?.visibility = View.VISIBLE
                itemView.ll3Row?.visibility = View.GONE
            }

            if (i % 2 == 1){
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowFinancialHighlight?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}