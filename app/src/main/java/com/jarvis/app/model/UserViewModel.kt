package com.jarvis.app.model

import android.arch.lifecycle.ViewModel
import android.widget.ArrayAdapter

class UserViewModel :ViewModel() {
    var title:String = ""

    var fragmentTag = ""
    var list:List<Any>? = ArrayList()
    var sAdapter:ArrayAdapter<String>? = null
    var summayList:ArrayList<String>? = ArrayList()

    var sortPerformanceSummary = 0
}