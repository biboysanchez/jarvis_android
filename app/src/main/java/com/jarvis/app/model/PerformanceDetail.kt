package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject

class PerformanceDetail(json:JSONObject) {
    val selectCategory  = json.string("select_type")
    val category        = json.string("category")
    val portfolio       = json.string("portfolio")
    val target          = json.string("target")
    val saham           = json.double("saham")
}