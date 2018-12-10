package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.dataholder.Constant
import com.jarvis.app.model.Table1
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*

class PerformanceSummaryAdapter : RecyclerView.Adapter<PerformanceSummaryAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Table1>? = ArrayList()
    private var selected = 0

    constructor(mContext: Context?, data: ArrayList<Table1>?, selected:Int) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PerformanceSummaryAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_home_list, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (data?.size!! > 4){
            5
        }else{
            data?.size!!
        }
    }

    override fun onBindViewHolder(p0: PerformanceSummaryAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int) {
            val obj = data?.get(position)
            itemView.tvRowTable1Name?.text = obj?.portFolio
            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#E2EEEA"))
            }

            when(selected){
                Constant.AUM -> {
                    itemView.tvRowTable1Value?.text = obj?.aum
                }

                Constant.RETURN_NAV -> {
                    itemView.tvRowTable1Value?.text = obj?.nav
                }

                Constant.RETURN_BMK -> {
                    itemView.tvRowTable1Value?.text = obj?.bmk
                }

                Constant.IR -> {
                    itemView.tvRowTable1Value?.text = obj?.informationRatio
                }

                Constant.YIELD -> {
                    itemView.tvRowTable1Value?.text = obj?.yield
                }

                Constant.VAR -> {
                    itemView.tvRowTable1Value?.text = obj?.varM
                }

                else ->{
                    itemView.tvRowTable1Value?.text = obj?.aum
                }
            }

            itemView.llBgRow?.setOnClickListener {
                val arrayList:List<ValueKey> = Arrays.asList(
                    ValueKey("AUM [Bn]",obj?.aum!!),
                    ValueKey("Return - NAV",obj.nav),
                    ValueKey("Return - BMK",obj.bmk),
                    ValueKey("Information Ratio",obj.informationRatio),
                    ValueKey("Yield",obj.yield),
                    ValueKey("Var",obj.varM)
                )
                DialogUtil.showCustomListDialog(mContext!!, obj.portFolio, arrayList)
            }
        }
    }
}