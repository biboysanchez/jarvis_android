package com.jarvis.app.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jarvis.app.R
import com.jarvis.app.adapter.ComparationAdapter
import java.util.*
import com.jarvis.app.adapter.DialogCompanyAdapter
import com.jarvis.app.dataholder.chart.ScatteredChartData
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.dialog_add_company.view.*
import kotlinx.android.synthetic.main.dialog_add_metrics.view.*
import kotlinx.android.synthetic.main.fragment_fixed_income.*

class FixedIncomeFragment : BaseFragment() {
    private var mAdapter: ComparationAdapter? = null
    var sAdapter: ComparationAdapter? = null

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        const val TAG = "FixedIncomeFragment"
        var instance: FixedIncomeFragment? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_fixed_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        setScatteredGraph()

        mAdapter = ComparationAdapter(context)
        sAdapter = ComparationAdapter(context)
        for (i in 0 until Comparation.getCompany().size){
            mAdapter?.addItem(Comparation.getCompany()[i])
            sAdapter?.addItem(Comparation.getCompany()[i])
        }

        setComparation()
        flShowCompany?.setOnClickListener {
            showAddCompanyDialog()
        }

        tvMetrics?.setOnClickListener {
            showMetricCompanyDialog()
        }
    }

    private fun setComparation(){
        sAdapter?.isHeader(false)
        mAdapter?.isHeader(true)
        rvComparationDetails?.layoutManager = LinearLayoutManager(context)
        rvComparationDetails?.adapter = sAdapter

        rvComparationCompany?.layoutManager = LinearLayoutManager(context)
        rvComparationCompany.adapter = mAdapter
    }

    private fun showAddCompanyDialog(){
        val alert = AlertDialog.Builder(context)
        val aView = LayoutInflater.from(context).inflate(R.layout.dialog_add_company, null)
        alert.setView(aView)

        val dialog = alert.create()
        aView.rvDialogList.layoutManager = LinearLayoutManager(context)

        val bAdapter = DialogCompanyAdapter(context)
        aView.rvDialogList.adapter = bAdapter
        aView.imgCloseDialog?.setOnClickListener {
            dialog.dismiss()
        }

        aView.btnAddCompany?.setOnClickListener {
            for (i in 0 until bAdapter.data!!.size){
                if (bAdapter.data!![i].isChecked){
                    this.mAdapter?.addItem(bAdapter.data!![i])
                    this.sAdapter?.addItem(bAdapter.data!![i])
                }
            }
            setComparation()
            dialog.dismiss()
        }

        dialog.show()
        dialog.setCancelable(false)
    }

    private fun showMetricCompanyDialog(){
        val alert = AlertDialog.Builder(context)
        val aView = LayoutInflater.from(context).inflate(R.layout.dialog_add_metrics, null)
        alert.setView(aView)

        val dialog = alert.create()
        aView.rvDialogMetricList?.layoutManager = LinearLayoutManager(context)
        val dAdapter = DialogCompanyAdapter(context)
        aView.rvDialogMetricList?.adapter = dAdapter
        aView.imgCloseDialogMetrics?.setOnClickListener {
            dialog.dismiss()
        }

        aView.btnAddMetrics?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.setCancelable(false)
    }

    private fun setSpinner(){
        spinnerEquities?.adapter = ArrayAdapter(context!!,
            R.layout.support_simple_spinner_dropdown_item,
            Arrays.asList("Danamas Saham","Simas Saham Bertumbuh") )

        spinnerEquities?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#9E9E9E"))
            }
        }
    }

    private fun setScatteredGraph(){
        ScatteredChartData().initChart(scatteredChart)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}