package com.jarvis.app.fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.dialog_pin.view.*
import kotlinx.android.synthetic.main.fragment_settings.*

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
        onCheckedListener()
    }

    private fun onCheckedListener(){
        switchCompat?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                showPinDialog()
            }
        }
    }

    private fun showPinDialog(){
        val alert = AlertDialog.Builder(context!!)
        val aView = LayoutInflater.from(context!!).inflate(R.layout.dialog_pin, null)
        alert.setView(aView)

        val dialog = alert.create()
        aView.btnCancelPin?.setOnClickListener {
            switchCompat?.isChecked = false
            dialog.dismiss()
        }

        aView.tvNextPin?.setOnClickListener {
            if (aView.etDialogPin?.text.toString().isEmpty() || aView.etDialogPin?.text?.length!! < 6){
                aView.etDialogPin?.error = "Invalid pin"
                return@setOnClickListener
            }
            aView.etDialogPin?.error = null

            if (aView.etDialogPin?.text.toString() != aView.etDialogVerifyPin?.text.toString()){
                aView.etDialogVerifyPin?.error = "Pin not match"
                return@setOnClickListener
            }

            aView.etDialogVerifyPin?.error = null
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
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