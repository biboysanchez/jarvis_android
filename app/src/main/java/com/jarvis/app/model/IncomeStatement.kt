package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject

class IncomeStatement (jsonObject: JSONObject) {
    var portfolio:String = jsonObject.string("portfolio")
    var cost = jsonObject.double("cost")
}