package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.fragment.CompanyDetailFragment
import com.jarvis.app.fragment.EquitiesFragment
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.row_comparition.view.*

class ComparationAdapter : RecyclerView.Adapter<ComparationAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: ArrayList<Comparation>? = ArrayList()
    private var isHeader = false

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    fun isHeader(isShow:Boolean){
        this.isHeader = isShow
    }

    fun addItem(comparation: Comparation){
        data?.add(comparation)
        notifyItemInserted(itemCount-1)
        notifyItemRangeInserted(itemCount-1, itemCount)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ComparationAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_comparition, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: ComparationAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val comparation = data!![i]
            val mActivity = mContext as MainActivity

            itemView.tvRowLoren?.text = comparation.lorem
            itemView.tvRowIpsum?.text = comparation.ipsum
            itemView.tvRowDolor?.text = comparation.dolor
            itemView.tvRowCompany?.text = comparation.company
            itemView.tvRowIndustry?.text = comparation.industry

            if (isHeader){
                itemView.llSideDetail?.visibility = View.GONE
                itemView.llMainColumn?.visibility = View.VISIBLE
            }else{
                itemView.llSideDetail?.visibility = View.VISIBLE
                itemView.llMainColumn?.visibility = View.GONE
            }

            itemView.imgRemoveCompany?.setOnClickListener {
                val indexOf = data?.indexOf(comparation)
                notifyItemRemoved(indexOf!!)
                EquitiesFragment.instance?.sAdapter?.notifyItemRemoved(indexOf)
                EquitiesFragment.instance?.sAdapter?.data?.removeAt(indexOf)
                data?.removeAt(indexOf)

                Handler().postDelayed({
                    notifyDataSetChanged()
                    EquitiesFragment.instance?.sAdapter?.notifyDataSetChanged()
                },700)
            }

            itemView.llRowComparation?.setOnClickListener {
                mActivity.viewModel?.selectedCompany = comparation
                mActivity.addFragment(CompanyDetailFragment(), CompanyDetailFragment.TAG)
            }

            if (i % 2 == 1){
                itemView.llRowComparation?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowComparation?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}