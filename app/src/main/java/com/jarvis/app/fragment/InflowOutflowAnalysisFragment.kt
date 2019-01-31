package com.jarvis.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R

class InflowOutflowAnalysisFragment : BaseFragment() {

    companion object {
        val TAG = "InflowOutflowAnalysisFragment"
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inflow_outflow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}