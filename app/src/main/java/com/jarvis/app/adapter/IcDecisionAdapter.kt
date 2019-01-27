package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.InvestmentDecision
import kotlinx.android.synthetic.main.row_investment_decision.view.*


class IcDecisionAdapter : RecyclerView.Adapter<IcDecisionAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var isHeader = false
    private var data = InvestmentDecision.investments()

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    fun isHeader(isShow:Boolean){
        this.isHeader = isShow
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): IcDecisionAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_investment_decision, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: IcDecisionAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val ic = data[i]
            itemView.tvRowColumn1?.text = ic.category
            itemView.tvRowColumn2?.text = ic.deadline
            itemView.imgColumn3.setImageResource(ic.img)

            if (isHeader){
                itemView.tvRowColumn1?.visibility = View.VISIBLE
                itemView.tvRowColumn2?.visibility = View.GONE
                itemView.imgColumn3?.visibility = View.GONE
            }else{
                itemView.tvRowColumn1?.visibility = View.GONE
                itemView.tvRowColumn2?.visibility = View.VISIBLE
                itemView.imgColumn3?.visibility = View.VISIBLE
            }

            if (i % 2 == 1){
                itemView.llRowDecisionRecap?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowDecisionRecap?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}