package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.dataholder.Constant
import com.jarvis.app.model.*
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class TimeSeriesAdapter : RecyclerView.Adapter<TimeSeriesAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<TableRisk>? = ArrayList()
    private var selected = 0
    private var isAll = false

    constructor(mContext: Context?, data: ArrayList<TableRisk>?, selected:Int, isAll:Boolean) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_home_list, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isAll){
            data?.size!!
        }else{
            if (data?.size!! > 4){
                5
            }else{
                data?.size!!
            }
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int) {
            val obj = data?.get(position)

            val list:List<ValueKey> = Arrays.asList(
                ValueKey(TableRisk.tableRiskDropDownList()[0], obj?.aum.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[1], obj?.yr3Ar.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[2], obj?.ytdAr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[3], obj?.targetAr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[4], obj?.y3Rr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[5], obj?.ytdRr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[6], obj?.targetRr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[7], obj?.portfolioSr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[8], obj?.bmkSr.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[9], obj?.varRc.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[10], obj?.varPercentRc.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[11], obj?.infoRatio.toString()),
                ValueKey(TableRisk.tableRiskDropDownList()[12], obj?.beta.toString())
            )

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }

            itemView.tvRowTable1Name?.text = obj?.group
            itemView.tvRowTable1Value?.text = String.format("%.2f", list[selected].value.toFloat())
            itemView.tvRowTable1SubTitle?.visibility = View.VISIBLE
            itemView.tvRowTable1SubTitle?.text = obj?.id

            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj!!.group, obj.id, list)
            }
        }
    }
}