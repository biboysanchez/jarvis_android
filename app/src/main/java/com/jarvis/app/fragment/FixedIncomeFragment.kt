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
import java.util.*
import kotlin.collections.ArrayList
import com.jarvis.app.adapter.DialogCompanyAdapter
import com.jarvis.app.dataholder.chart.ScatteredChartData
import com.jarvis.app.model.Comparation
import kotlinx.android.synthetic.main.dialog_add_company.view.*
import kotlinx.android.synthetic.main.dialog_add_metrics.view.*
import kotlinx.android.synthetic.main.fragment_fixed_income.*
import kotlinx.android.synthetic.main.row_company_industry.view.*
import kotlinx.android.synthetic.main.simple_text.view.*
import java.lang.Exception

class FixedIncomeFragment : BaseFragment() {
    private var arrayCompany:ArrayList<Comparation>? = ArrayList()

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

        arrayCompany = ArrayList()
        for (i in 0 until Comparation.getComparation().size){
            setComparation(Comparation.getComparation()[i])
        }

        flShowCompany?.setOnClickListener {
            showAddCompanyDialog()
        }

        tvMetrics?.setOnClickListener {
            showMetricCompanyDialog()
        }
    }

    private fun setComparation(comparation: Comparation){
        arrayCompany?.add(comparation)
        val mView0 = LayoutInflater.from(context).inflate(R.layout.row_company_industry, null)
        mView0.tvRowCompany?.text = comparation.company
        mView0.tvRowIndustry?.text = comparation.industry
        mView0.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            144
        )

        val mView1 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
        mView1.tvSimpleText?.text = comparation.lorem
        mView1.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            144
        )

        val mView2 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
        mView2.tvSimpleText?.text = comparation.ipsum
        mView2.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            144
        )

        val mView3 = LayoutInflater.from(context).inflate(R.layout.simple_text, null)
        mView3.tvSimpleText?.text = comparation.dolor
        mView3.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            144
        )

        llCol0.addView(mView0)
        llCol1.addView(mView1)
        llCol2.addView(mView2)
        llCol3.addView(mView3)

        for (i in 0 until arrayCompany!!.size){
            if (i % 2 == 1){
                mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }

        mView0.imgRemoveCompany?.setOnClickListener {
            try {
                llCol1.removeView(mView1)
                llCol2.removeView(mView2)
                llCol3.removeView(mView3)
                llCol0.removeView(mView0)
                arrayCompany?.remove(comparation)

                for (i in 0 until arrayCompany!!.size){
                    if (i % 2 == 1){
                        mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                        mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                        mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                        mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                    }else{
                        mView0.llRowComparationMain?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                        mView1.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                        mView2.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                        mView3.tvSimpleText?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun showAddCompanyDialog(){
        val alert = AlertDialog.Builder(context)
        val aView = LayoutInflater.from(context).inflate(R.layout.dialog_add_company, null)
        alert.setView(aView)

        val dialog = alert.create()
        aView.rvDialogList.layoutManager = LinearLayoutManager(context)

        val mAdapter = DialogCompanyAdapter(context)
        aView.rvDialogList.adapter = mAdapter
        aView.imgCloseDialog?.setOnClickListener {
            dialog.dismiss()
        }

        aView.btnAddCompany?.setOnClickListener {
            for (i in 0 until mAdapter.data!!.size){
                if (mAdapter.data!![i].isChecked){
                    setComparation(mAdapter.data!![i])
                }
            }
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
        val mAdapter = DialogCompanyAdapter(context)
        aView.rvDialogMetricList?.adapter = mAdapter
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