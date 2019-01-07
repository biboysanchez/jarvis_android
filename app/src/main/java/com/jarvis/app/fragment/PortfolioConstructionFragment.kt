package com.jarvis.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jarvis.app.R
import com.jarvis.app.adapter.NewsAndAnalysisAdapter
import com.jarvis.app.adapter.SummaryParamAdapter
import com.jarvis.app.adapter.WatchListAdapter
import com.jarvis.app.utils.CustomBottomSheet
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_portfolio_construction.*

class PortfolioConstructionFragment: BaseFragment() {
    private lateinit var mView:View
    private val arrWatchListDropdown = arrayOf(
        "Credit Rating", "Date Added", "Coupon Div", "Yield", "Amount Issued", "Time to Maturity", "Market Price", "Txt Volume"
    )

    private val arrSummartParamsDropdown = arrayOf(
        "Net Profit Margin", "Return on Asset", "Return on Equity", "Net Debt to Equity", "Price Earning Ratio", "BBG Consesus Rating", "Company Scoring", "Company Rating"
    )

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    fun title(){
        mActivity?.toolbar?.title = "Portfolio Construction"
    }

    companion object {
        const val TAG = "PortfolioConstructionFragment"
        var instance:PortfolioConstructionFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        mView = inflater.inflate(R.layout.fragment_portfolio_construction, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshAll()
    }

    fun refreshAll(){
        setWatchList()
        setSummaryParameters()
        setNewsAndAnalysis()
    }

    private fun setWatchList(){
        spinnerWatchlist?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, arrWatchListDropdown)

        spinnerWatchlist?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
            }
        }

        imgMenuWatchList?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortLease!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                   // mActivity?.sortLease = selectedSorted
                }
            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        rvWatchList?.layoutManager = LinearLayoutManager(context)
        rvWatchList?.adapter = WatchListAdapter(context)
    }

    private fun setSummaryParameters(){
        spinnerSummaryParams?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, arrSummartParamsDropdown)

        spinnerSummaryParams?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
            }
        }

        imgSummaryParams?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortLease!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                   // mActivity?.sortLease = selectedSorted
                }
            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }

        rvSummaryParams?.layoutManager = LinearLayoutManager(context)
        rvSummaryParams?.adapter = SummaryParamAdapter(context)
    }

    private fun setNewsAndAnalysis(){
        rvNewsAnalysis?.layoutManager = LinearLayoutManager(context)
        rvNewsAnalysis?.adapter = NewsAndAnalysisAdapter(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

}