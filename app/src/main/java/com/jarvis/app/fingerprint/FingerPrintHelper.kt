package com.jarvis.app.fingerprint

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import androidx.annotation.RequiresApi
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException

class FingerPrintHelper  {
    private var cipher: Cipher? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null
    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null
    private var context:Context? = null
    var fingerprintHandler:FingerprintHandler? = null

    private var alertDialog:AlertDialog.Builder? = null

    constructor(context: Context?) {
        this.context = context
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            init()
        }else{
            Log.i("TAG", "lower version")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun init() {
        keyguardManager = context?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fingerprintManager = context?.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        alertDialog = AlertDialog.Builder(context!!)
        alertDialog?.setPositiveButton("OK", null)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isSupported():Boolean{
        if (!fingerprintManager!!.isHardwareDetected) {
            alertDialog?.setTitle("Your device doesn't support fingerprint authentication")
            alertDialog?.show()
            return false
        }
        return true
    }

    fun isPermissionEnabled():Boolean{
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            alertDialog?.setTitle("Please enable the fingerprint permission")
            alertDialog?.show()
            return false
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasEnrolledFingerprint():Boolean{
        if (!fingerprintManager!!.hasEnrolledFingerprints()) {
            alertDialog?.setTitle("No fingerprint configured. Please register at least one fingerprint in your device's Settings")
            alertDialog?.show()
            return false
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isKeyguardSecure():Boolean{
        if (!keyguardManager!!.isKeyguardSecure) {
            alertDialog?.setTitle("Please enable lockscreen security in your device's Settings")
            alertDialog?.show()
            return false
        } else {
            try {
                generateKey()
            } catch (e: FingerprintException) {
                e.printStackTrace()
            }

            if (initCipher()) {
                cryptoObject = FingerprintManager.CryptoObject(cipher!!)
                fingerprintHandler = FingerprintHandler(context)
                fingerprintHandler?.startAuth(fingerprintManager!!, cryptoObject!!)
            }
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Throws(FingerprintException::class)
    private fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyStore!!.load(
                null
            )
            keyGenerator!!.init(
                KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )

            keyGenerator!!.generateKey()

        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: CertificateException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: IOException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun initCipher(): Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore!!.load(null)
            val key = keyStore!!.getKey(KEY_NAME, null) as SecretKey
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }

    private inner class FingerprintException(e: Exception) : Exception(e)

    companion object {
        private val KEY_NAME = "yourKey"
    }
}
