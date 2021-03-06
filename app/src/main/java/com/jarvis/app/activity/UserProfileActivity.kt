package com.jarvis.app.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.adapter.SummaryExposureAdapter
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.row_duration.view.*
import kotlinx.android.synthetic.main.row_user_recommendation.view.*

class UserProfileActivity : BaseFragment() {
    companion object {
        val TAG = "UserProfileActivity"
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLatestStatus()
        setSummaryExposure()
        setRecommendation()
    }
    
    private fun setLatestStatus(){
        rvUserLatestStatus?.layoutManager = LinearLayoutManager(context)
        rvUserLatestStatus?.adapter = LatestStatusAdapter(context)
    }

    private fun setSummaryExposure(){
        val mAdapter: SummaryExposureAdapter? = SummaryExposureAdapter(context)
        val sAdapter:SummaryExposureAdapter? = SummaryExposureAdapter(context)

        rvSummaryCompanies?.layoutManager    = LinearLayoutManager(context)
        mAdapter?.isHeader(true)
        rvSummaryCompanies?.adapter          = sAdapter

        rvSummaryRevenues?.layoutManager = LinearLayoutManager(context)
        mAdapter?.isHeader(true)
        rvSummaryRevenues?.adapter      = mAdapter
    }

    private fun setRecommendation(){
        rvUserRecommendation?.layoutManager = LinearLayoutManager(context)
        rvUserRecommendation?.adapter = RecommendationAdapter(context)
    }

    inner class LatestStatusAdapter: RecyclerView.Adapter<LatestStatusAdapter.ViewHolder> {
        private var mContext: Context? = null
        constructor(mContext: Context?) : super() {
            this.mContext = mContext
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LatestStatusAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_duration, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 9
        }

        override fun onBindViewHolder(p0: LatestStatusAdapter.ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                itemView.tvRowAssetName?.text = "Decision 123"
                itemView.tvRowAssetValue.text = "Pending"
                if (i % 2 == 1) {
                    itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#FFFFFF"))
                } else {
                    itemView.llRowAsset?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                }
            }
        }
    }

    inner class RecommendationAdapter: RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
        private var mContext: Context? = null
        private val data = StaticData.userRecommendationList()

        constructor(mContext: Context?) : super() {
            this.mContext = mContext
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecommendationAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_user_recommendation, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(p0: RecommendationAdapter.ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                val item = data[i]
                itemView.imgDrawable?.setImageResource(item.icon)
                itemView.rowParentText?.text = item.name
                itemView.rowSideMenu?.setOnClickListener {
                    when (i){
                        0 -> {
                            mActivity?.viewModel?.title = "Portfolio Overview"
                            mActivity?.getPage("1")
                        }

                        1 -> {
                            mActivity?.viewModel?.title = "Currency Research"
                            mActivity?.getPage("30")
                        }

                        2 -> {
                            mActivity?.viewModel?.title = "IC Decision Recap"
                            mActivity?.getPage("00")
                        }

                        3 -> {
                            mActivity?.viewModel?.title = "Summary Exposure"
                            mActivity?.getPage("31")
                        }
                    }
                }

            }
        }
    }
}
