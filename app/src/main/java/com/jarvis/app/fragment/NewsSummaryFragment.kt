package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity

class NewsSummaryFragment : Fragment() {
    private var mActivity:MainActivity?  = null

    companion object {
        val TAG = "NewsSummaryFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        mActivity?.showBackButton(true)
        mActivity?.title  = "News Summary"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
        mActivity?.title = mActivity?.viewModel?.title
    }
}