package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject

class Table1(json:JSONObject) {
    val portFolio        = json.string("portfolio")
    val aum              = json.string("aum")
    val realized         = json.string("realized")
    val target           = json.string("target")
    val informationRatio = json.string("information_ratio")
    val yield            = json.string("yield")
    var varM             = json.string("var")
    val nav              = json.string("return_nav")
    val bmk              = json.string("return_bmk")
}