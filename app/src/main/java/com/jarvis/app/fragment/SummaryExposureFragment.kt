package com.jarvis.app.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.R.id.rvBalanceSheetAsset
import com.jarvis.app.R.id.rvSummaryExposure
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.*
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_summary_exposure.*

class SummaryExposureFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "SummaryExposureFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summary_exposure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBlankTitle?.text = mActivity?.viewModel!!.title
        drawAll()
        setAutoSearch()

        tvShowAllSummaryExposure?.setOnClickListener {
            mActivity?.addFragment(SummaryExposureAllFragment(),SummaryExposureAllFragment.TAG)
        }


    }

    private fun setAutoSearch(){
        val arr = arrayOf("Bond A", "Bond B", "Bond C", "Bond D", "Bond E", "Bond F", "Sinarmas", "Bond H")
        autoCorrectSearchBalance?.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
            arr))

        autoCorrectIncome?.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
        arr))

        searchSheetExposure()
        searchSensitivity()
    }

    private fun searchSheetExposure(){
        var isShow = false
        searchSheetExposure?.setOnClickListener {
            if (isShow){
                isShow = false
                autoCorrectSearchBalance?.dismissDropDown()
            }else {
                isShow = true
                autoCorrectSearchBalance?.showDropDown()
            }
        }
    }

    private fun searchSensitivity(){
        var isShow = false
        searchSensitivity?.setOnClickListener {
            if (isShow){
                isShow = false
                autoCorrectIncome?.dismissDropDown()
            }else {
                isShow = true
                autoCorrectIncome?.showDropDown()
            }
        }
    }

    private fun drawAll(){
        setBalanceSheetAsset()
        setBalanceSheetLiabilities()
        setIncomeStatementSensitivity()
        setSummaryExposure()
    }

    private fun setSummaryExposure(){
        rvSummaryExposure?.layoutManager = LinearLayoutManager(context)
        rvSummaryExposure?.adapter = SummaryExposureAdapter(context, false)
    }

    private fun setBalanceSheetAsset(){
        rvBalanceSheetAsset?.layoutManager = LinearLayoutManager(context)
        rvBalanceSheetAsset?.adapter = BalanceSheetAssetAdapter(context)
    }

    private fun setBalanceSheetLiabilities(){
        rvBalanceLiabilities?.layoutManager = LinearLayoutManager(context)
        rvBalanceLiabilities?.adapter = BalanceSheetLiabilitiesAdapter(context)
    }

    private fun setIncomeStatementSensitivity(){
        rvIncomeStatement?.layoutManager = LinearLayoutManager(context)
        rvIncomeStatement?.adapter = IncomeStatementAdapter(context)
    }
}