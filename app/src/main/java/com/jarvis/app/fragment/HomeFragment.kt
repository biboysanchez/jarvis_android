package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.dataholder.chart.PieChart
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {
    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPieChart()
        setSpinners()
    }

    private fun setPieChart(){
        val pieChart = PieChart(activity, pieChartHome)
        pieChart.data()
    }

    private fun setSpinners(){
        spinnerAssetClass?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Asset Class"))

        spinnerCompanyType?.adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item, Arrays.asList("Company Type"))

        Util.changeTextColor(spinnerAssetClass)
        Util.changeTextColor(spinnerCompanyType)
    }
}