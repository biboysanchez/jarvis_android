package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity

class CompanyDetailFragment : Fragment() {

    private var mActivity:MainActivity? = null

    companion object {
        val TAG = "CompanyDetailFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_company_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = context as MainActivity?
        mActivity?.showBackButton(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
    }
}