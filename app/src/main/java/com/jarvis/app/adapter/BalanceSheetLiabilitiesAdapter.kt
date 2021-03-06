package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.row_balance_sheet_liabilities.view.*

class BalanceSheetLiabilitiesAdapter : RecyclerView.Adapter<BalanceSheetLiabilitiesAdapter.ViewHolder> {
    private var mContext:Context? = null

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BalanceSheetLiabilitiesAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_balance_sheet_liabilities, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(p0: BalanceSheetLiabilitiesAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        fun bindItem(i:Int){
            if (i == 0){
                val prime = 409
                val max = 1000
                itemView.tvProgressTitle?.text = "Current Liabilities"
                itemView.progressAsset?.max = max
                itemView.progressAsset?.progress = prime
                itemView.progressAsset?.secondaryProgress = prime + 204
            }else{
                val prime = 448
                val max = 1000
                itemView.tvProgressTitle?.text = "Long Term Liabilities"
                itemView.progressAsset?.max = max
                itemView.progressAsset?.progress = prime
                itemView.progressAsset?.secondaryProgress = prime + 536
            }
        }
    }
}