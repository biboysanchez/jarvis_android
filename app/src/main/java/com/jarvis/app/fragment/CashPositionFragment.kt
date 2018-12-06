package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.adapter.CashFlowAdapter
import com.jarvis.app.adapter.CashPlacementAdapter
import kotlinx.android.synthetic.main.layout_cash_flow_summary.*
import kotlinx.android.synthetic.main.layout_cash_placement.*

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
    }

    private fun setRecyclerView(){
        rvSelection?.layoutManager = LinearLayoutManager(context)
        rvSelection?.adapter = CashFlowAdapter(context, null)

        rvAssets?.layoutManager = LinearLayoutManager(context)
        rvAssets?.adapter = CashPlacementAdapter(context, null)
    }
}