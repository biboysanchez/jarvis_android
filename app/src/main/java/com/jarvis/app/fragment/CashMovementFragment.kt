package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.CashMovementAdapter
import kotlinx.android.synthetic.main.fragment_cash_movement.*

class CashMovementFragment : Fragment() {

    private var mActivity:MainActivity? = null

    companion object {
        val TAG = "CashMovementFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cash_movement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        configToolbar("Cash Movement", true)
        setCashMovementRecyclerView()
    }

    private fun setCashMovementRecyclerView(){
        rvCashMovement?.layoutManager = LinearLayoutManager(context)
        rvCashMovement?.adapter = CashMovementAdapter(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        configToolbar(mActivity?.viewModel!!.title, false)
    }

    private fun configToolbar(title:String, isShowBack:Boolean){
        mActivity?.title = title
        mActivity?.showBackButton(isShowBack)
    }

}