package com.jarvis.app.model

import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject

class Portfolio (json:JSONObject) {
    var selectType  = json.string("select_type")
    var portfolio   = json.string("portfolio")
    var holding     = json.string("holding")
    var date        = json.string("date")
    var target      = json.string("target")
    var dateLong    = 0L

}