package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject

class Cumulative (json:JSONObject) {
    var month = json.string("month")
    var value = json.double("value")
}