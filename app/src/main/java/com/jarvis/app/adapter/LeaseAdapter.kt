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
import com.jarvis.app.model.Table5
import com.jarvis.app.model.Table6
import com.jarvis.app.model.ValueKey
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*

class LeaseAdapter : RecyclerView.Adapter<LeaseAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Table6>? = ArrayList()
    private var selected = 0
    private var isAll = false

    constructor(mContext: Context?, data: ArrayList<Table6>?, selected:Int, isAll:Boolean?) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll!!
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
                ValueKey(Table6.table6DropdownList()[0], obj?.estDays.toString()),
                ValueKey(Table6.table6DropdownList()[1], obj?.estTransactions.toString()),
                ValueKey(Table6.table6DropdownList()[2], obj?.bidAskCost.toString())
            )

            itemView.tvRowTable1Name?.text = obj?.securities
            itemView.tvRowTable1Value?.text = list[selected].value

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }

            itemView.llBgRow?.setOnClickListener {
                DialogUtil.showCustomListDialog(mContext!!, obj!!.securities, list)
            }
        }
    }
}