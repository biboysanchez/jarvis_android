package com.jarvis.app.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.model.RiskReturn
import kotlinx.android.synthetic.main.fragment_risk_return.*

class RiskReturnSimulationFragment : Fragment() {
    private var mActivity:MainActivity? = null
    val riskReturn = RiskReturn()

    companion object {
        val TAG = "RiskReturnSimulationFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_risk_return, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = context as MainActivity?
        mActivity?.showBackButton(true)
        mActivity?.title = "Risk-Return Simulation"
        fields()

        btnApplySimulation?.setOnClickListener {
            riskReturn.cash             = if(etCashInput?.text.toString().isNotEmpty()) {etCashInput?.text.toString().toFloat()} else 0f
            riskReturn.equity           = if(etEquitiesInput?.text.toString().isNotEmpty()) {etEquitiesInput?.text.toString().toFloat()} else 0f
            riskReturn.governmentBonds  = if(etGovernmentInput?.text.toString().isNotEmpty()) {etGovernmentInput?.text.toString().toFloat()} else 0f
            riskReturn.corporateBonds   = if(etCorporateInput?.text.toString().isNotEmpty()) {etCorporateInput?.text.toString().toFloat()} else 0f
            StrategicAssetAllocationFragment.instance?.createNewSimulation(riskReturn)
            mActivity?.fm?.popBackStack()
        }
    }

    private fun fields(){
        inputSimulation(imgCashBtnLeft, imgCashBtnRight, etCashInput)
        inputSimulation(imgEquitiesBtnLeft, imgEquitiesBtnRight, etEquitiesInput)
        inputSimulation(imgGovernmentBtnLeft, imgGovernmentBtnRight, etGovernmentInput)
        inputSimulation(imgCorporateBtnLeft, imgCorporateBtnRight, etCorporateInput)
    }

    private fun inputSimulation(viewL: View?, viewR: View?, input:EditText?){
        var int = 0
        viewR?.setOnClickListener {
            if (input?.text!!.isNotEmpty()){
                int = input.text.toString().toInt()
            }

            int += 1
            input.setText(int.toString())
        }

        viewL?.setOnClickListener {
            if (input?.text!!.isNotEmpty()){
                int = input.text.toString().toInt()
            }

            int -= 1
            if (int <= 0){
                int = 0
            }
            input.setText(int.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
        mActivity?.title = mActivity?.viewModel?.title
    }
}