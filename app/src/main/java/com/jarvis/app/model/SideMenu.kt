package com.jarvis.app.model

class SideMenu {
    var name = ""
    var icon = 0
    var list:List<String>? = ArrayList()

    constructor(name: String, icon: Int, list: List<String>) {
        this.name = name
        this.icon = icon
        this.list = list
    }
}