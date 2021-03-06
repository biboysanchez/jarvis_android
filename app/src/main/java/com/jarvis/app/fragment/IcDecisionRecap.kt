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
import com.jarvis.app.adapter.IcDecisionAdapter
import com.jarvis.app.adapter.SummaryFinancialAdapter
import kotlinx.android.synthetic.main.fragment_ic_decision_recap.*
import java.lang.Exception

class IcDecisionRecap : BaseFragment() {

    private var mAdapter: IcDecisionAdapter? = null
    private var sAdapter: IcDecisionAdapter? = null

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "IcDecisionRecap"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ic_decision_recap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        setRecyclerAdapter()
    }

    private fun setRecyclerAdapter(){
        mAdapter = IcDecisionAdapter(context)
        sAdapter = IcDecisionAdapter(context)

        sAdapter?.isHeader(true)
        mAdapter?.isHeader(false)
        rvDecisionCategories?.layoutManager = LinearLayoutManager(context)
        rvDecisionCategories?.adapter = sAdapter

        rvDecisionDeadline?.layoutManager = LinearLayoutManager(context)
        rvDecisionDeadline.adapter = mAdapter
    }

    private fun setSpinner(){
        val arr = arrayOf(
            "Week of 3 December 2018",
            "Week of 10 December 2018",
            "Week of 17 December 2018",
            "Week of 24 December 2018",
            "Week of 31 December 2018",
            "Week of 7 December 2018",
            "Week of 14 January 2019")
        spinnerInvestmentDecision?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, arr)
        spinnerInvestmentDecision?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#424242"))
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}