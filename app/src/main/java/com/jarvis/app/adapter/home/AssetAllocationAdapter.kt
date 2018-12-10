package com.jarvis.app.adapter.home

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
import java.util.*
import kotlin.collections.ArrayList

class AssetAllocationAdapter : RecyclerView.Adapter<AssetAllocationAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Table4>? = ArrayList()
    private var selected = 0

    constructor(mContext: Context?, data: ArrayList<Table4>?, selected:Int) : super() {
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

            if (position % 2 == 1){
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }else{
                itemView.llBgRow?.setBackgroundColor(Color.parseColor("#E2EEEA"))
            }
            itemView.tvRowTable1Name?.text = obj?.assetClassSector
            itemView.tvRowTable1Value?.text = obj?.portfolio

            val list:List<ValueKey> = Arrays.asList(
                ValueKey("Decision date", obj?.decisionDate!!),
                ValueKey("Asset class sector", obj.assetClassSector),
                ValueKey("Action", obj.action)
            )

            itemView.llBgRow?.setOnClickListener {
               DialogUtil.showCustomListDialog(mContext!!, obj.assetClassSector, list)
            }
        }
    }
}