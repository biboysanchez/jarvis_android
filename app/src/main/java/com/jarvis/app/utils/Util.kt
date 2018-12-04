package com.jarvis.app.utils

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import java.text.NumberFormat

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
}