package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject

class CashPlacement (json:JSONObject) {
    var id          = json.string("_id")
    var amount      = json.double("amount")
    var avgYield    = json.double("avg_yield")
    var bank        = json.string("bank")
}