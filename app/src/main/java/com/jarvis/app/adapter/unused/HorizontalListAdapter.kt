package com.jarvis.app.adapter.unused

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.fragment.unused_old.*
import com.jarvis.app.model.Company
import kotlinx.android.synthetic.main.button_scroll.view.*

class HorizontalListAdapter : RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<Company>? = ArrayList()

    constructor(mContext: Context?, data: List<Company>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.button_scroll, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mActivity = mContext as MainActivity

        fun bindItem(i: Int) {
            val company = data?.get(i)
            itemView.tvBtnText?.text = company?.name
            if (company?.isSelected!!){
                if (mActivity.selectedCompany.isEmpty()){
                    mActivity.selectedCompany = company.name
                }

                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_light)
                itemView.tvBtnText?.isEnabled = false
            }else{
                itemView.tvBtnText?.setBackgroundResource(R.drawable.rounded_primary)
                itemView.tvBtnText?.isEnabled = true
            }

            itemView.tvBtnText?.setOnClickListener {
                for (a in 0 until data!!.size){
                    data!![a].isSelected = false
                }

                data!![i].isSelected = true
                mActivity.selectedCompany = company.name
                HomeFragment.instance?.refreshAll()
                PerformanceDetailFragment.instance?.refreshAll()
                TimeSeriesFragment.instance?.refreshAll()
                DurationMatchFragment.instance?.refreshAll()
                CashPositionFragment.instance?.refreshAll()
                notifyDataSetChanged()
            }
        }

    }
}