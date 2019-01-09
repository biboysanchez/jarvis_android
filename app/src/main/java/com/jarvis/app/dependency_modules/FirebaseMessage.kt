package com.bingwit.consumer.dependency_modules

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity

import org.json.JSONObject

class FirebaseMessage : FirebaseMessagingService(){
    val TAG = "Firebase"
    lateinit var notif_json : JSONObject
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        Log.d(TAG, "Notification Message TITLE: " + p0?.notification?.title)
        Log.d(TAG, "Notification Message BODY: " + p0?.notification?.body)
        Log.d(TAG, "Notification Message DATA: " + p0?.data.toString())

        Log.d("Firebase",p0?.from)
        Log.d("Firebase",p0?.data.toString())
        // Check if message contains a data payload.
        if(p0 != null){
            if (p0.data.size > 0) {
                Log.d(TAG, "Message data payload: " + p0.data)
            }
            notif_json = JSONObject(p0.data.toString()).optJSONObject("default")
            // Check if message contains a notification payload.
            if (p0.notification != null) {
                Log.d(TAG, "Message data payload: " + p0.notification?.body)
            }

            val intent = Intent(this, MainActivity::class.java)
            val pending_intent : PendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
            val notification_ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val bmp = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.img_notif_big)
            val sound = Uri.parse("android.resource://" + packageName + "/" + R.raw.notification)
            val notification_compat_builder = NotificationCompat.Builder(this)
                    .setLargeIcon(bmp)
                    .setSmallIcon(R.drawable.img_notif_small)
                    .setContentTitle(notif_json.optString("title"))
                    .setContentText(notif_json.optString("body"))
                    .setSound(notification_ringtone)
                    .setContentIntent(pending_intent)
            val notify_manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notify_manager.notify(0,notification_compat_builder.build())
        }
    }

}