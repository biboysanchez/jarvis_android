package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.adapter.HomeListAdapter
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_time_series.*
import java.util.*

class TimeSeriesFragment : BaseFragment() {
    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "TimeSeriesFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerAdapter()
        setSpinner()
    }

    private fun setSpinner(){
        spinnerPerformance?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, Arrays.asList("JCI", "Saham", "Target") )
        spinnerSelection?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Simas Saham Bertumbuh") )

        Util.changeTextColor(spinnerPerformance, "#9E9E9E")
        Util.changeTextColor(spinnerSelection, "#9E9E9E")
    }

    private fun setRecyclerAdapter(){


        rvPerformanceAttribute?.layoutManager = LinearLayoutManager(context)
        rvPerformanceAttribute?.adapter = HomeListAdapter(context, null)
    }
}