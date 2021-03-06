package com.jarvis.app.adapter.home

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.Table2
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class SecuritySelectionAdapter : RecyclerView.Adapter<SecuritySelectionAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<Table2>? = ArrayList()
    private var selected = 0
    private var isAll = false

    constructor(mContext: Context?, data: List<Table2>?, selected:Int, isAll:Boolean) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll
    }

    fun sortSecurity(sorter: Int) {
        when (sorter) {
            0 -> {
                data = data?.sortedWith(compareBy {it.company})
            }

            1 -> {
                data = data?.sortedByDescending { it.company }
            }

            2 -> {
                when (selected) {
                    1 -> {
                        data = data?.sortedByDescending { it.amountIc }
                    }

                    2 -> {
                        data = data?.sortedByDescending { it.ttm }
                    }

                    3 -> {
                        data = data?.sortedByDescending { it.splitedRange }
                    }

                    4 -> {
                        data = data?.sortedByDescending { it.amountReal}
                    }

                    5 -> {
                        data = data?.sortedByDescending { it.avgPrice}
                    }

                    6 -> {
                        data = data?.sortedByDescending { it.percentage}
                    }

                }

            }

            else -> {
                when (selected) {
                    1 -> {
                        data = data?.sortedWith(compareBy { it.amountIc })
                    }

                    2 -> {
                        data = data?.sortedWith(compareBy { it.ttm })
                    }

                    3 -> {
                        data = data?.sortedWith(compareBy { it.splitedRange })
                    }

                    4 -> {
                        data = data?.sortedWith(compareBy { it.amountReal})
                    }

                    5 -> {
                        data = data?.sortedWith(compareBy { it.avgPrice})
                    }

                    6 -> {
                        data = data?.sortedWith(compareBy { it.percentage})
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
                ValueKey(Table2.table2DropDownList()[0], obj?.action!!),
                ValueKey(Table2.table2DropDownList()[1], obj.amountIc.toString()),
                ValueKey(Table2.table2DropDownList()[2], obj.ttm.toString()),
                ValueKey(Table2.table2DropDownList()[3], obj.range),
                ValueKey(Table2.table2DropDownList()[4], obj.amountReal.toString()),
                ValueKey(Table2.table2DropDownList()[5], obj.avgPrice.toString()),
                ValueKey(Table2.table2DropDownList()[6], obj.strPercentage)
            )

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }

           // val parted = obj.range.split("-")[0]
           // Log.i("TAG", "splited string $parted")

            itemView.tvRowTable1Name?.text = obj.company
            itemView.tvRowTable1Value?.text = list[selected].value
            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj.company, list)
            }
        }
    }
}