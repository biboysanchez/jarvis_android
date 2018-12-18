package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject

class Matching (json:JSONObject) {
    var month       = json.string("month")
    var assets      = json.double("assets")
    var lisbility   = json.double("liability")
    var liabilities = json.double("liabilities")
}