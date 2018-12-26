package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.*
import com.jarvis.app.utils.DialogUtil
import kotlinx.android.synthetic.main.row_home_list.view.*
import java.util.*
import kotlin.collections.ArrayList

class TimeSeriesAdapter : RecyclerView.Adapter<TimeSeriesAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<TableRisk>? = ArrayList()
    private var selected = 0
    private var isAll = false

    constructor(mContext: Context?, data: List<TableRisk>?, selected:Int, isAll:Boolean) : super() {
        this.mContext = mContext
        this.data = data
        this.selected = selected
        this.isAll = isAll
    }

    fun sortPerformanceAndRisk(sorter: Int) {
        when (sorter) {
            0 -> {
                data = data?.sortedWith(compareBy {it.group})
            }

            1 -> {
                data = data?.sortedByDescending { it.group }
            }

            2 -> {
                data = when (selected) {
                    0 -> {
                        data?.sortedByDescending { it.aum }
                    }

                    1 -> {
                        data?.sortedByDescending { it.yr3Ar }
                    }

                    2 -> {
                        data?.sortedByDescending { it.ytdAr }
                    }

                    3 -> {
                        data?.sortedByDescending { it.targetAr }
                    }

                    4 -> {
                        data?.sortedByDescending { it.y3Rr }
                    }

                    5 -> {
                        data?.sortedByDescending { it.ytdRr }
                    }

                    6 -> {
                        data?.sortedByDescending { it.targetRr }
                    }

                    7 -> {
                        data?.sortedByDescending { it.portfolioSr }
                    }

                    8 -> {
                        data?.sortedByDescending { it.bmkSr }
                    }

                    9 -> {
                        data?.sortedByDescending { it.varRc }
                    }

                    10 -> {
                        data?.sortedByDescending { it.varPercentRc }
                    }

                    11 -> {
                        data?.sortedByDescending { it.infoRatio }
                    }

                    else -> {
                        data?.sortedByDescending { it.beta }
                    }
                }

            }

            else -> {
                data = when (selected) {

                    0 -> {
                        data?.sortedWith(compareBy { it.aum })
                    }

                    1 -> {
                        data?.sortedWith(compareBy { it.yr3Ar })
                    }

                    2 -> {
                        data?.sortedWith(compareBy { it.ytdAr })
                    }

                    3 -> {
                        data?.sortedWith(compareBy { it.targetAr })
                    }

                    4 -> {
                        data?.sortedWith(compareBy { it.y3Rr })
                    }

                    5 -> {
                        data?.sortedWith(compareBy { it.ytdRr })
                    }

                    6 -> {
                        data?.sortedWith(compareBy { it.targetRr })
                    }

                    7 -> {
                        data?.sortedWith(compareBy { it.portfolioSr })
                    }

                    8 -> {
                        data?.sortedWith(compareBy { it.bmkSr })
                    }

                    9 -> {
                        data?.sortedWith(compareBy { it.varRc })
                    }

                    10 -> {
                        data?.sortedWith(compareBy { it.varPercentRc })
                    }

                    11 -> {
                        data?.sortedWith(compareBy { it.infoRatio })
                    }

                    else -> {
                        data?.sortedWith(compareBy { it.beta })
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
                ValueKey(TableRisk.tableRiskDropDownList()[0], String.format("%.2f", obj?.aum?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[1], String.format("%.2f", obj?.yr3Ar?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[2], String.format("%.2f", obj?.ytdAr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[3], String.format("%.2f", obj?.targetAr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[4], String.format("%.2f", obj?.y3Rr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[5], String.format("%.2f", obj?.ytdRr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[6], String.format("%.2f", obj?.targetRr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[7], String.format("%.2f", obj?.portfolioSr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[8], String.format("%.2f", obj?.bmkSr?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[9], String.format("%.2f", obj?.varRc?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[10], String.format("%.2f", obj?.varPercentRc?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[11], String.format("%.2f", obj?.infoRatio?.toFloat())),
                ValueKey(TableRisk.tableRiskDropDownList()[12], String.format("%.2f", obj?.beta?.toFloat()))
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