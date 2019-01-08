package com.jarvis.app.activity

import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.jarvis.app.R
import com.jarvis.app.sessions.UserSession
import kotlinx.android.synthetic.main.activity_pin.*
import android.view.animation.AnimationUtils
import com.jarvis.app.fingerprint.FingerPrintHelper
import com.jarvis.app.fingerprint.FingerprintHandler
import android.view.animation.Animation



class AuthenticationActivity : AppCompatActivity() {
    private var mSession:UserSession? = null
    private var fingerPrintHelper: FingerPrintHelper? = null

    companion object {
        var instance:AuthenticationActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        instance = this
        mSession = UserSession(this)
        fingerPrintHelper = FingerPrintHelper(this)
        checkFingerPrint()

        tvGoToLogin?.setOnClickListener {
            val intent = Intent(this@AuthenticationActivity, LoginActivity::class.java)
            startActivity(intent)
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
//        val slideDown = AnimationUtils.loadAnimation(
//            applicationContext,
//            R.anim.slide_down
//        )
//        val slideUp = AnimationUtils.loadAnimation(
//            applicationContext,
//            R.anim.slide_up
//        )
//
//        llAuthErr.startAnimation(slideDown)
        Handler().postDelayed({llAuthErr?.visibility = View.GONE},2000)
    }

    private fun startActivity(){
        val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        instance = null
    }
}
