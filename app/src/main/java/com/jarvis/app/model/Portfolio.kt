package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject

class Portfolio (json:JSONObject) {
    val selectType  = json.string("select_type")
    val portfolio   = json.string("portfolio")
    val holding     = json.string("holding")
    val date        = json.string("date")
    val target      = json.string("target")
}