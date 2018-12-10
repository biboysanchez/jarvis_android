package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

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

    companion object {
        fun table1DropdownList():List<String>{
            return Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR")
        }
    }
}