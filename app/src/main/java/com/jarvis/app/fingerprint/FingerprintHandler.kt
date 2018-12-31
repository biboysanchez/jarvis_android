package com.jarvis.app.fingerprint

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.lang.reflect.InvocationTargetException

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler : FingerprintManager.AuthenticationCallback {

    private var cancellationSignal: CancellationSignal? = null
    private var context: Context? = null
    var callback:AuthenticationCallback? = null

    interface AuthenticationCallback{
        fun onSuccessCallback(result: FingerprintManager.AuthenticationResult)
    }

    constructor(context: Context?) : super() {
        this.context = context
    }

    fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
        cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationError(
        errMsgId: Int,
        errString: CharSequence
    ) {
        Toast.makeText(
            context,
            "Authentication error\n$errString",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(
            context,
            "Authentication failed",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAuthenticationHelp(
        helpMsgId: Int,
        helpString: CharSequence
    ) {
        Toast.makeText(
            context,
            "Authentication help\n$helpString",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAuthenticationSucceeded(
        result: FingerprintManager.AuthenticationResult
    ) {
        callback?.onSuccessCallback(result)
        getFingerprintInfo(context!!)
//        Toast.makeText(
//            context,
//            "Success!",
//            Toast.LENGTH_LONG
//        ).show()
    }
//1754129644
    //1770906860
    private fun getFingerprintInfo(context: Context) {
        try {
            val fingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            val method = FingerprintManager::class.java.getDeclaredMethod("getEnrolledFingerprints")
            val obj = method.invoke(fingerprintManager)

            if (obj != null) {
                val clazz = Class.forName("android.hardware.fingerprint.Fingerprint")
                val getFingerId = clazz.getDeclaredMethod("getFingerId")

                for (i in 0 until (obj as List<*>).size) {
                    val item = obj[i]
                    if (item != null) {
                        System.out.println("fkie4. fingerId: " + getFingerId.invoke(item))
                    }
                }
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }


}