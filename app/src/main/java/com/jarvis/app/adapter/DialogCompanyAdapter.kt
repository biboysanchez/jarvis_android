package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.row_comparator_company.view.*

class DialogCompanyAdapter : RecyclerView.Adapter<DialogCompanyAdapter.ViewHolder> {
    private var mContext: Context? = null
    var data: List<Comparation>? = Comparation.getCompany()
    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DialogCompanyAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_comparator_company, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: DialogCompanyAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val comparation = data!![i]
            itemView.cbCompany?.text = comparation.company

            itemView.cbCompany?.setOnCheckedChangeListener { buttonView, isChecked ->
                data!![i].isChecked = isChecked
            }

            if (i % 2 == 1){
                itemView.llRowComparationCompany?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowComparationCompany?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}