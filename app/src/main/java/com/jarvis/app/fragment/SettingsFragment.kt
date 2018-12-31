package com.jarvis.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R

class SettingsFragment: BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "SettingsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configToolBar(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        configToolBar(false)
    }

    private fun configToolBar(isShow:Boolean){
        isShowBack(isShow)
        mActivity?.isShowCompany(isShow)
    }
}