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
import android.widget.Toast
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.extension.simpleDialog
import com.jarvis.app.model.RiskReturn
import kotlinx.android.synthetic.main.fragment_risk_return.*
import kotlinx.android.synthetic.main.row_simulation.view.*

class RiskReturnSimulationFragment : Fragment() {
    private val titles = arrayListOf("Cash", "Equities", "Government Bonds", "Corporate Bonds")
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

        btnApplySimulation?.setOnClickListener {
            StrategicAssetAllocationFragment.instance?.createNewSimulation(riskReturn)
            mActivity?.fm?.popBackStack()
        }

        setSimulation()
    }

    private fun setSimulation(){
        rvSimulation?.layoutManager = LinearLayoutManager(context)
        rvSimulation?.adapter = SimulationAdapter(context)
    }

    inner class SimulationAdapter : RecyclerView.Adapter<SimulationAdapter.ViewHolder> {
        private var mContext: Context? = null

        constructor(mContext: Context?) : super() {
            this.mContext = mContext
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimulationAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_simulation, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return titles.size
        }

        override fun onBindViewHolder(p0: SimulationAdapter.ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                itemView.tvRowSimulationTitle?.text = titles[i]

                itemView.flBtnLeft?.setOnClickListener {
                    context?.simpleDialog("", "Please use manual input. Button function in progress")
                }

                itemView.flBtnRight?.setOnClickListener {
                    context?.simpleDialog("", "Please use manual input. Button function in progress")
                }


                itemView.etSimulationInput?.addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        when (i){
                            0 -> {
                                riskReturn.cash = if (s.toString().isNotEmpty()){
                                    s.toString().toFloat()
                                }else {
                                    0f
                                }
                            }

                            1 -> {
                                riskReturn.equity = if (s.toString().isNotEmpty()){
                                    s.toString().toFloat()
                                }else {
                                    0f
                                }
                            }

                            2 -> {
                                riskReturn.governmentBonds = if (s.toString().isNotEmpty()){
                                    s.toString().toFloat()
                                }else {
                                    0f
                                }

                            }

                            3 -> {
                                riskReturn.corporateBonds = if (s.toString().isNotEmpty()){
                                    s.toString().toFloat()
                                }else {
                                    0f
                                }
                            }
                        }
                    }

                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.showBackButton(false)
        mActivity?.title = mActivity?.viewModel?.title
    }
}