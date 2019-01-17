package com.jarvis.app.fragment.unused

import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.extension.toast
import com.jarvis.app.fingerprint.FingerPrintHelper
import com.jarvis.app.fingerprint.FingerprintHandler
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.sessions.UserSession
import kotlinx.android.synthetic.main.dialog_authenticate_fingerprint.view.*
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: BaseFragment() {
    private var fingerPrintHelper:FingerPrintHelper? = null
    private var mSession:UserSession? = null

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
        mSession = UserSession(context!!)
        fingerPrintHelper = FingerPrintHelper(context)
        onCheckedListener()

        Handler().postDelayed({mActivity?.showBackButton(true)},500)
        mActivity?.isHideCompany(true)
    }

    private fun onCheckedListener(){
       switchCompat.isChecked = mSession?.isActive!!

        switchCompat?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                if (!fingerPrintHelper!!.isSupported()){
                    switchCompat?.isChecked = false
                    return@setOnCheckedChangeListener
                }

                if (!fingerPrintHelper!!.isPermissionEnabled()){
                    switchCompat?.isChecked = false
                    return@setOnCheckedChangeListener
                }

                if (!fingerPrintHelper!!.hasEnrolledFingerprint()){
                    switchCompat?.isChecked = false
                    return@setOnCheckedChangeListener
                }

                if (!fingerPrintHelper!!.isKeyguardSecure()){
                    switchCompat?.isChecked = false
                    return@setOnCheckedChangeListener
                }
                showFingerprintAuthentication()
            }else{
                mSession?.deAuthorize()
            }
        }
    }

    private fun showFingerprintAuthentication(){
        val alert = AlertDialog.Builder(context!!)
        val aView = LayoutInflater.from(context!!).inflate(R.layout.dialog_authenticate_fingerprint, null)
        alert.setView(aView)

        val dialog = alert.create()
        aView.btnCancelAuthentication?.setOnClickListener {
            switchCompat?.isChecked = false
            dialog.dismiss()
        }

        fingerPrintHelper?.fingerprintHandler?.callback = object :FingerprintHandler.AuthenticationCallback{
            override fun onAuthError(error: CharSequence) {
                Log.i(TAG, "error: $error")
                context?.toast("Authentication failed\n$error")
            }

            override fun onSuccessCallback(result: FingerprintManager.AuthenticationResult) {
                mSession?.setIsLogged()
                dialog.dismiss()
                context?.toast("Successfully authenticated")
            }
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity?.lastIndex = "00"
        mActivity?.showBackButton(false)
        mActivity?.isHideCompany(false)

        fingerPrintHelper = null
    }
}