package com.jarvis.app.activity

import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.jarvis.app.R
import com.jarvis.app.sessions.UserSession
import kotlinx.android.synthetic.main.activity_pin.*
import android.view.animation.AnimationUtils
import com.jarvis.app.fingerprint.FingerPrintHelper
import com.jarvis.app.fingerprint.FingerprintHandler

class PinActivity : AppCompatActivity() {
    private var mSession:UserSession? = null
    private var fingerPrintHelper: FingerPrintHelper? = null

    companion object {
        var instance:PinActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        instance = this

        mSession = UserSession(this)
        fingerPrintHelper = FingerPrintHelper(this)
        checkFingerPrint()
        onInputChanged()

        tvGoToLogin?.setOnClickListener {
            val intent = Intent(this@PinActivity, LoginActivity::class.java)
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
            override fun onSuccessCallback(result: FingerprintManager.AuthenticationResult) {
                startActivity()
            }
        }
    }

    private fun onInputChanged(){
        etPinCode?.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == mSession?.pin()){
                    startActivity()
                }else{
                    if (s?.length == 6){
                        val animShake = AnimationUtils.loadAnimation(this@PinActivity, R.anim.shake)
                        etPinCode?.startAnimation(animShake)
                    }
                }
            }
        })
    }

    private fun startActivity(){
        val intent = Intent(this@PinActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
        instance = null
    }
}
