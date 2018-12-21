package com.jarvis.app.adapter.home

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.Table3
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class TopTenAdapter : RecyclerView.Adapter<TopTenAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<Table3>? = ArrayList()
    private var selected = 0
    private var isAll = false

    constructor(mContext: Context?, data: List<Table3>?, selected:Int, isAll:Boolean) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll
    }

    fun sortPerformance(sorter: Int) {

        when (sorter) {
            0 -> {
                data = data?.sortedWith(compareBy { it.securities })!!
            }

            1 -> {
                data = data?.sortedByDescending { it.securities }
            }

            2 -> {
                when (selected) {
                    0 -> {
                        data = data?.sortedByDescending { it.national }
                    }

                    1 -> {
                        data = data?.sortedByDescending { it.avgCost }
                    }

                    2 -> {
                        data = data?.sortedByDescending { it.currentPrice }
                    }

                    3 -> {
                        data = data?.sortedByDescending { it.unrealized }
                    }

                    else -> {
                        data = data?.sortedByDescending { it.riskContribution }
                    }

                }

            }

            else -> {
                when (selected) {
                    0 -> {
                        data = data?.sortedWith(compareBy { it.national })
                    }

                    1 -> {
                        data = data?.sortedWith(compareBy { it.avgCost })
                    }

                    2 -> {
                        data = data?.sortedWith(compareBy { it.currentPrice })
                    }

                    3 -> {
                        data = data?.sortedWith(compareBy { it.unrealized })
                    }

                    else -> {
                        data = data?.sortedWith(compareBy { it.riskContribution})
                    }
                }
            }
        }

        notifyDataSetChanged()
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
                ValueKey(Table3.table3DropDownList()[0], obj?.national!!.toString()),
                ValueKey(Table3.table3DropDownList()[1], obj.avgCost.toString()),
                ValueKey(Table3.table3DropDownList()[2], obj.currentPrice.toString()),
                ValueKey(Table3.table3DropDownList()[3], obj.unrealized.toString()),
                ValueKey(Table3.table3DropDownList()[4], obj.riskContribution.toString())
            )

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }

            itemView.tvRowTable1Name?.text = obj.securities
            itemView.tvRowTable1Value?.text = list[selected].value

            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj.securities, list)
            }
        }
    }
}