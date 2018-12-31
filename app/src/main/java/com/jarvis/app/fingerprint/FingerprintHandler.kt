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

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler : FingerprintManager.AuthenticationCallback {

    private var cancellationSignal: CancellationSignal? = null
    private var context: Context? = null

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
        Toast.makeText(
            context,
            "Success!",
            Toast.LENGTH_LONG
        ).show()
    }
}