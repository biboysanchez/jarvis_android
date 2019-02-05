package com.jarvis.app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.fingerprint.FingerPrintHelper
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.sessions.UserSession
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    companion object {
        var instance:LoginActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        instance = this
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

//        try {
//            val helper = FingerPrintHelper(this)
//            helper.isSupported()
//        }catch (e:Exception){
//            e.printStackTrace()
//        }

        btnLogin?.setOnClickListener {
            editLoginUsername?.setText("admin")
            editLoginPassword?.setText("password")
            checkLoginFields()
        }
    }

    private fun checkLoginFields(){
        if (editLoginUsername?.text?.trim()!!.isEmpty()){
            inputLoginEmail?.error = "Username is required"
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
        params["username"] = editLoginUsername?.text.toString()
        params["password"] = editLoginPassword?.text.toString()

        ApiRequest.post(this, API.login, params, object :ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(this@LoginActivity, response)){
                    val mSession = UserSession(this@LoginActivity)
                    mSession.authorize(JSONObject(response))
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    override fun onBackPressed() {
        if (UserSession(this).isActive){
            val fingerPrintHelper = FingerPrintHelper(this)
            if (fingerPrintHelper.isSupported()){
                val intent = Intent(this@LoginActivity, AuthenticationActivity::class.java)
                startActivity(intent)
            }
            finish()
        }else{
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
