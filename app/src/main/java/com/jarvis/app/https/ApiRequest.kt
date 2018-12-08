package com.jarvis.app.https

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jarvis.app.R
import com.jarvis.app.extension.simpleDialog
import com.jarvis.app.sessions.UserSession
import dmax.dialog.SpotsDialog
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

object ApiRequest {
    private const val TAG = "ApiRequest"

    /**
     * Callback response from api request use of all API request in this class.
     */
    interface URLCallback {
        fun didURLResponse(response: String)
        fun didURLFailed(error: VolleyError?)
    }

    /**
     * Simple api request post no parameters
     */
    fun post(context: Context, url: String, url_callback: URLCallback?) {
        Log.i(TAG, "URL: $url")

        val pDialog = SpotsDialog(context, R.style.SpotDialogStyle)
        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            pDialog.dismiss()
            url_callback?.didURLResponse(response)
        }, Response.ErrorListener { error ->
            pDialog.dismiss()
            url_callback?.didURLFailed(error)
        }) {
        }
        request.retryPolicy = getRetryPolicy()
        queue.add(request)
    }

    /**
     * Simple api request post with parameters
     */
    fun post(context: Context, url: String, params: MutableMap<String, String>, url_callback: URLCallback?) {

        val pDialog = SpotsDialog(context, R.style.SpotDialogStyle)
        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            pDialog.dismiss()
            Log.i(TAG, "URL: $url \n$response")
            url_callback?.didURLResponse(response)
        }, Response.ErrorListener { error ->
            pDialog.dismiss()
            url_callback?.didURLFailed(error)
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
               // params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
//            override fun getBodyContentType(): String {
//                return "application/x-www-form-urlencoded; charset=$paramsEncoding"
//            }
        }
        request.retryPolicy = getRetryPolicy()
        queue.add(request)
    }

    private fun getRetryPolicy(): DefaultRetryPolicy {
        return DefaultRetryPolicy(
            48 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            -1,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }

    /**
     * Show simple alert dialog with title and message to display error upon failed API request.
     *
     * @param context Base context
     * @param error A volley error received from API request error.
     */
    fun showVolleyError(context: Context, error: VolleyError?) {
        if (error?.networkResponse != null && error.networkResponse.data != null) {
            try {
                val body: String?
                try {
                    body = String(error.networkResponse.data, Charset.forName("UTF-8"))
                    Log.e(TAG, "showVolleyError: $body")
                    val errMsg = JSONObject(body).getJSONObject("error").getString("message")
                    context.simpleDialog("Failed!", errMsg)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }catch (e:Exception){
                e.printStackTrace()
                context.simpleDialog("Request Error!", "There's an error occurred while requesting in web server. Please try again later")
            }
        }
    }

}