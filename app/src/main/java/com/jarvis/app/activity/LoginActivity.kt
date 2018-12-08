package com.jarvis.app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        btnLogin?.setOnClickListener {
            checkLoginFields()
        }
    }

    private fun checkLoginFields(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()

        if (editLoginEmail?.text?.trim()!!.isEmpty()){
            inputLoginEmail?.error = "Email is required"
            return
        }
        inputLoginEmail?.error = null

        if (editLoginPassword?.text?.trim()!!.isEmpty()){
            inputLoginPassword?.error = "Password is required"
            return
        }
        inputLoginPassword?.error = null

    }

    private fun postEmail(){
        val params = HashMap<String, String>()
        ApiRequest.post(this, API.login, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(response)){

                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
