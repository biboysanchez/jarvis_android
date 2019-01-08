package com.jarvis.app.activity

import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.jarvis.app.R
import com.jarvis.app.sessions.UserSession
import kotlinx.android.synthetic.main.activity_pin.*
import com.jarvis.app.fingerprint.FingerPrintHelper
import com.jarvis.app.fingerprint.FingerprintHandler

class AuthenticationActivity : AppCompatActivity() {
    private var mSession:UserSession? = null
    private var fingerPrintHelper: FingerPrintHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        mSession = UserSession(this)
        fingerPrintHelper = FingerPrintHelper(this)
        checkFingerPrint()

        tvGoToLogin?.setOnClickListener {
            val intent = Intent(this@AuthenticationActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            fingerPrintHelper = null
        }
    }

    private fun checkFingerPrint(){
        if (!fingerPrintHelper!!.isSupported()){
            return
        }

        if (!fingerPrintHelper!!.isPermissionEnabled()){
            return
        }

        if (!fingerPrintHelper!!.hasEnrolledFingerprint()){
            return
        }

        if (!fingerPrintHelper!!.isKeyguardSecure()){
            return
        }

        fingerPrintHelper?.fingerprintHandler?.callback = object : FingerprintHandler.AuthenticationCallback{
            override fun onAuthError(error: CharSequence) {
                showAuthError()
            }

            override fun onSuccessCallback(result: FingerprintManager.AuthenticationResult) {
                startActivity()
            }
        }
    }

    private fun showAuthError(){
        llAuthErr?.visibility = View.VISIBLE
        Handler().postDelayed({llAuthErr?.visibility = View.GONE},2000)
    }

    private fun startActivity(){
        LoginActivity.instance?.finish()
        val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
