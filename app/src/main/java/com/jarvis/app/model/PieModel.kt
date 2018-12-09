package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject

class PieModel(json: JSONObject) {
    val period      = json.string("period")
    val weekId      = json.string("week_id")
    val company     = json.string("company")
    val category    = json.string("category")
    val item        = json.string("item")
    val percentage  = json.double("percentage")
    val portofolio  = json.double("portfolio")
    val timeStamp   = json.int("rec_timestamp")
    val pKey        = json.string("pkey")
    var color       = 0
}