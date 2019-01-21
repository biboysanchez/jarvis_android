package com.jarvis.app.fragment.unused

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.adapter.SummaryExposureAdapter
import kotlinx.android.synthetic.main.fragment_summary_exposure_all.*

class SummaryExposureAllFragment : Fragment() {
    var mActivity:MainActivity? = null

    companion object {
        val TAG = "SummaryExposureAllFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_summary_exposure_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        mActivity?.showBackButton(true)

        rvSummaryExposureAll?.layoutManager = LinearLayoutManager(context)
        rvSummaryExposureAll?.adapter = SummaryExposureAdapter(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
    }
}