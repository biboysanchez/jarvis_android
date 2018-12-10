package com.jarvis.app.model

import android.arch.lifecycle.ViewModel

class UserViewModel :ViewModel() {
    var title:String = ""
    var selectedCompany = ""
    var selectedWeek = ""
    var selectedCategory1 = ""
    var selectedCategory2 = ""
}