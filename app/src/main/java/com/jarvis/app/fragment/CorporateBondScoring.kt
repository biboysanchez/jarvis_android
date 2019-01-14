package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.BondScoreAdapter
import com.jarvis.app.adapter.KeyIndicatorAdapter
import com.jarvis.app.adapter.NewsAdapter
import com.jarvis.app.adapter.TopicAdapter
import com.jarvis.app.model.BondScore
import kotlinx.android.synthetic.main.fragment_corporate_bond_scoring.*

class CorporateBondScoring : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "CorporateBondScoring"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_corporate_bond_scoring, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity
        setAll()
    }

    private fun setAll(){
        setSearchView()
        setKeyIndicators()
        setSummaryTopic()
        setSummaryNews()
        setTop10HighestScore()
        setTop10WorstScore()
    }

    private fun setSearchView(){
        val arr = arrayOf("Bond A", "Bond B", "Bond C", "Bond D", "Bond E", "Bond F", "Sinarmas", "Bond H")
        autoCompleteCorporate?.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, arr))

        var isShow = false
        imgSearchCompany?.setOnClickListener {
            if (isShow){
                isShow = false
                autoCompleteCorporate?.dismissDropDown()
            }else {
                isShow = true
                autoCompleteCorporate?.showDropDown()
            }
        }
    }

    private fun setKeyIndicators(){
        rvKeyIndicator?.layoutManager = LinearLayoutManager(context)
        rvKeyIndicator?.adapter = KeyIndicatorAdapter(context)
    }

    private fun setSummaryTopic(){
        rvNewsSummaryTopic?.layoutManager = LinearLayoutManager(context)
        rvNewsSummaryTopic?.adapter = TopicAdapter(context)
    }

    private fun setSummaryNews(){
        rvNewsSummaryNews?.layoutManager = LinearLayoutManager(context)
        rvNewsSummaryNews?.adapter = NewsAdapter(context)
    }

    private fun setTop10HighestScore(){
        rvHighScore?.layoutManager = LinearLayoutManager(context)
        rvHighScore?.adapter = BondScoreAdapter(context, BondScore.getHighestScore())
    }

    private fun setTop10WorstScore(){
        rvWorstScore?.layoutManager = LinearLayoutManager(context)
        rvWorstScore?.adapter = BondScoreAdapter(context, BondScore.getWorstScore())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.isHideCompany(false)
    }
}