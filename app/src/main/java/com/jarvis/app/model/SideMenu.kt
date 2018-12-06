package com.jarvis.app.model

class SideMenu {
    var name = ""
    var icon = 0
    var list:List<String>? = ArrayList()
    var isExpanded = false

    constructor(name: String, icon: Int, list: List<String>, isExpanded:Boolean) {
        this.name = name
        this.icon = icon
        this.list = list
        this.isExpanded = isExpanded
    }
}