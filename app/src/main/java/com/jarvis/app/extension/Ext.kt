package com.jarvis.app.extension

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.Toast

fun Context.toast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.simpleDialog(title: String, message:String){
    val alert = AlertDialog.Builder(this)
    alert.setTitle(title)
    alert.setMessage(message)
    alert.setPositiveButton("Ok", null)
    alert.show()
}