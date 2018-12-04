package com.jarvis.app.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import java.text.NumberFormat
import android.util.DisplayMetrics
import android.support.v7.app.AppCompatActivity



object Util {
    fun changeTextColor(spinner:Spinner?){
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#757575"))
            }
        }
    }

    fun priceFormat(price:Float):String{
        val defaultFormat = NumberFormat.getCurrencyInstance()
        val value = defaultFormat.format(price.toInt())
        return value.substring(1, value.length)
    }

    fun setUnderlineText(textView:TextView?){
        textView?.paintFlags = textView?.paintFlags!! or Paint.UNDERLINE_TEXT_FLAG
    }

    fun getWidth(activity: AppCompatActivity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

    fun getHeight(activity: AppCompatActivity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    fun GetDipsFromPixel(context: Context, pixels: Float): Int {
        // Get the screen's density scale
        val scale = context.resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 0.5f).toInt()
    }

}