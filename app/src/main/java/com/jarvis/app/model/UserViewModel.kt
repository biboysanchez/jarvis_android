package com.jarvis.app.model

import android.arch.lifecycle.ViewModel

class UserViewModel :ViewModel() {
    var title:String = ""

    var fragmentTag = ""
    var list:List<Any>? = ArrayList()
}