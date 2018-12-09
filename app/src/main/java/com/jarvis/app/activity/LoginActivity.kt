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
        postLogin()
    }

    private fun postLogin(){
        val params = HashMap<String, String>()
        params["username"] = editLoginEmail?.text.toString()
        params["password"] = editLoginPassword?.text.toString()

        ApiRequest.post(this, API.login, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(response)){
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }
}
