package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ArrayRes
import com.jarvis.app.R
import com.jarvis.app.adapter.CashFlowAdapter
import com.jarvis.app.adapter.CashPlacementAdapter
import com.jarvis.app.model.CashPlacement
import kotlinx.android.synthetic.main.fragment_cash_placement.*

class CashPlacementDetailFragment : BaseFragment() {
    private var arrCashFlow:ArrayList<CashPlacement>? = ArrayList()
    override fun setTitle(): String {
        return mActivity?.viewModel?.fragmentTag!!
    }

    companion object {
        var TAG = "CashPlacementDetailFragment"
        fun newInstance(arrCashFlow: ArrayList<CashPlacement>):Fragment{
            val frag = CashPlacementDetailFragment()
            frag.arrCashFlow = arrCashFlow
            return frag
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cash_placement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isShowBack(true)
        setRecyclerView()
    }

    private fun setRecyclerView(){
        rvCashPlacement?.layoutManager = LinearLayoutManager(context)
        rvCashPlacement?.adapter = CashPlacementAdapter(context, arrCashFlow, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isShowBack(false)
        CashPositionFragment.instance?.title()
    }

}