package com.jarvis.app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.row_bottom_sheet.view.*
import java.util.*
import java.util.logging.Handler

class CustomBottomSheet : BottomSheetDialogFragment() {
    var selectedIndex = 0
    var mCalback:SorterCallback? = null


    interface SorterCallback{
        fun onSortSelected(selectedSorted:Int)
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        val contentView = View.inflate(context, R.layout.bottom_sheet, null)
        dialog?.setContentView(contentView)

        contentView.rvBottomSheet?.layoutManager = LinearLayoutManager(context)
        contentView.rvBottomSheet?.adapter = BottomSheetAdapter(context)

        contentView.imgCloseDialog?.setOnClickListener {
            this.dismiss()
        }
    }

    inner class BottomSheetAdapter : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
        private var mContext: Context? = null
        private var list = Arrays.asList(
            "Name - Ascending",
            "Name - Descending",
            "Value - High to Low",
            "Value - Low to High"
        )

        constructor(mContext: Context?) : super() {
            this.mContext = mContext
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_bottom_sheet, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(position: Int) {
                val obj = list[position]
                itemView.tvRowBottomSheet?.text = obj

                itemView.llBottomSheet?.setOnClickListener {
                    selectedIndex = position
                    this@CustomBottomSheet.mCalback?.onSortSelected(selectedIndex)
                    notifyDataSetChanged()
                    this@CustomBottomSheet.dismiss()
                }

                if (position == selectedIndex){
                    itemView.imgCheck?.visibility = View.VISIBLE
                    itemView.llBottomSheet?.setBackgroundColor(Color.parseColor("#D2ECE8"))
                }else{
                    itemView.imgCheck?.visibility = View.GONE
                    itemView.llBottomSheet?.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}