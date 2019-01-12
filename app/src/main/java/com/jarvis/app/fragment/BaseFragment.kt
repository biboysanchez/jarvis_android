package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.jarvis.app.activity.MainActivity
import kotlinx.android.synthetic.main.app_bar_main.*

abstract class BaseFragment : Fragment() {
    var mActivity:MainActivity? = null

    abstract fun setTitle(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = context as MainActivity
        mActivity?.toolbar?.title = setTitle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mActivity?.fm?.backStackEntryCount == 1){
            mActivity?.toolbar?.title = mActivity?.mainTitle
        }else{
            PerformanceDetailFragment.instance?.title()
        }
    }

    fun isShowBack(isShow:Boolean){
        mActivity?.showBackButton(isShow)
      //  mActivity?.isHideCompany(isShow)
    }
}