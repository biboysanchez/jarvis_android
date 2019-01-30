package com.jarvis.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.model.CashPlacement
import com.jarvis.app.model.SideMenu
import kotlinx.android.synthetic.main.layout_top_10.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.row_cash_placement.view.*
import kotlinx.android.synthetic.main.row_duration.view.*
import kotlinx.android.synthetic.main.row_home_list.view.*
import kotlinx.android.synthetic.main.row_progress.view.*
import kotlinx.android.synthetic.main.section_parent.view.*
import kotlinx.android.synthetic.main.section_second.view.*

class CashPlacementAdapter : RecyclerView.Adapter<CashPlacementAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<CashPlacement>? = ArrayList()
    private var isAll = false

    constructor(mContext: Context?, data: List<CashPlacement>?, isAll:Boolean) : super() {
        this.mContext = mContext
        this.data = data
        this.isAll = isAll
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CashPlacementAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_cash_placement, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isAll){
            data?.size!!
        }else{
            5
        }
    }

    override fun onBindViewHolder(p0: CashPlacementAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItem(i: Int) {
            val placement = data!![i]
            val percent = (placement.avgYield * 100).toFloat()
            val mYield = String.format("%.2f", percent)

            itemView.tvRowAsset?.text = placement.bank
            if (placement.amount > 0.0){
                itemView.tvRowAmount?.text = String.format("%.2f", placement.amount.toFloat())
            }else {
                itemView.tvRowAmount?.text = ""
            }

            if (percent > 0){
                itemView.tvRowYield?.text = "$mYield%"
            }else {
                itemView.tvRowYield?.text = ""
            }

            if (i % 2 == 1){
                itemView.llRowCashPlacement?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowCashPlacement?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }

    }
}