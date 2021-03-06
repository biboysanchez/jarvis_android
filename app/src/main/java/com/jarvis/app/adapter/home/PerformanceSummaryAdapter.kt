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
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*

class PerformanceSummaryAdapter : RecyclerView.Adapter<PerformanceSummaryAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<Table1>? = ArrayList()
    private var selected = 0
    private var isAll = false


    constructor(mContext: Context?, data: List<Table1>?, selected:Int, isAll:Boolean?) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll!!
    }


    fun sortPerformance(sorter: Int){
        when(sorter){
            0 -> {
                data =  data?.sortedWith(compareBy { it.portFolio})!!
            }

            1 -> {
                data = data?.sortedByDescending { it.portFolio }
            }

            2 -> {
                when (selected){
                    0 -> {
                        data = data?.sortedByDescending { it.aum }
                    }

                    1 -> {
                        data = data?.sortedByDescending { it.realized }
                    }

                    2 -> {
                        data = data?.sortedByDescending { it.target }
                    }

                    3 -> {
                        data = data?.sortedByDescending { it.informationRatio }
                    }

                    4 -> {
                        data = data?.sortedByDescending { it.yield }
                    }

                    else -> {
                        data = data?.sortedByDescending { it.varM }
                    }
                }

            }

            else ->{
                when (selected){
                    0 -> {
                        data = data?.sortedWith(compareBy  { it.aum })
                    }

                    1 -> {
                        data = data?.sortedWith(compareBy  { it.realized })
                    }

                    2 -> {
                        data = data?.sortedWith(compareBy  { it.target })
                    }

                    3 -> {
                        data = data?.sortedWith(compareBy  { it.informationRatio })
                    }

                    4 -> {
                        data = data?.sortedWith(compareBy  { it.yield })
                    }

                    else -> {
                        data = data?.sortedWith(compareBy  { it.varM })
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
                ValueKey(Table1.table1DropdownList()[0],  String.format("%.2f", obj?.aum!!.toFloat())),
                ValueKey(Table1.table1DropdownList()[1], String.format("%.2f", obj.realized)),
                ValueKey(Table1.table1DropdownList()[2], String.format("%.2f", obj.target)),
                ValueKey(Table1.table1DropdownList()[3], String.format("%.2f", obj.informationRatio)),
                ValueKey(Table1.table1DropdownList()[4], String.format("%.2f", obj.yield)),
                ValueKey(Table1.table1DropdownList()[5], String.format("%.2f", obj.varM))
            )

            itemView.tvRowTable1Name?.text = obj.portFolio
            itemView.tvRowTable1Value?.text = list[selected].value

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }


            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj.portFolio, list)
            }
        }
    }
}