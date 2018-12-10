package com.jarvis.app.adapter.home

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.dataholder.Constant
import com.jarvis.app.model.Table1
import com.jarvis.app.model.Table2
import com.jarvis.app.model.Table3
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class TopTenAdapter : RecyclerView.Adapter<TopTenAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Table3>? = ArrayList()
    private var selected = 0

    constructor(mContext: Context?, data: ArrayList<Table3>?, selected:Int) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
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

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(position: Int) {
            val obj = data?.get(position)

            val list:List<ValueKey> = Arrays.asList(
                ValueKey(Table3.table3DropDownList()[0], obj?.national!!),
                ValueKey(Table3.table3DropDownList()[1], obj.avgCost.toString()),
                ValueKey(Table3.table3DropDownList()[2], obj.currentPrice.toString()),
                ValueKey(Table3.table3DropDownList()[3], obj.unrealized.toString()),
                ValueKey(Table3.table3DropDownList()[4], obj.riskContribution.toString())
            )

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#E2EEEA"))
            }
            itemView.tvRowTable1Name?.text = obj.securities
            itemView.tvRowTable1Value?.text = list[selected].value

            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj.securities, list)
            }
        }
    }
}