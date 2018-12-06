package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.jarvis.app.activity.MainActivity

open class BaseFragment : Fragment() {
    private var mActivity:MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = context as MainActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // mActivity?.showBackButton(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}