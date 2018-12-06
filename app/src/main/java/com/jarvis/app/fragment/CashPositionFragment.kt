package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.adapter.CashFlowAdapter
import com.jarvis.app.adapter.CashPlacementAdapter
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.layout_cash_flow_summary.*
import kotlinx.android.synthetic.main.layout_cash_movement.*
import kotlinx.android.synthetic.main.layout_cash_placement.*
import java.util.*

class CashPositionFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "CashPositionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cash_position, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSpinner()
    }

    private fun setRecyclerView(){
        rvSelection?.layoutManager = LinearLayoutManager(context)
        rvSelection?.adapter = CashFlowAdapter(context, null)

        rvAssets?.layoutManager = LinearLayoutManager(context)
        rvAssets?.adapter = CashPlacementAdapter(context, null)
    }

    private fun setSpinner(){
        spinnerCashMovement?.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(
            "Week 1 - Sep 2018", "Week 2 - Sep 2018", "Week 3 - Sep 2018", "Week 4 - Sep 2018"))
        spinnerCashMovement?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        Util.changeTextColor(spinnerCashMovement, "#9E9E9E")
    }
}