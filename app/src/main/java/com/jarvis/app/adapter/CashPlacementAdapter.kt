package com.jarvis.app.adapter

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
import com.jarvis.app.model.SideMenu
import kotlinx.android.synthetic.main.layout_top_10.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.row_cash_placement.view.*
import kotlinx.android.synthetic.main.row_home_list.view.*
import kotlinx.android.synthetic.main.row_progress.view.*
import kotlinx.android.synthetic.main.section_parent.view.*
import kotlinx.android.synthetic.main.section_second.view.*

class CashPlacementAdapter : RecyclerView.Adapter<CashPlacementAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<String>? = ArrayList()

    constructor(mContext: Context?, data: List<String>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CashPlacementAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_cash_placement, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(p0: CashPlacementAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            if (i % 2 == 1){
                itemView.llRowCashPlacement?.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }else{
                itemView.llRowCashPlacement?.setBackgroundColor(Color.parseColor("#E2EEEA"))
            }
        }

    }
}