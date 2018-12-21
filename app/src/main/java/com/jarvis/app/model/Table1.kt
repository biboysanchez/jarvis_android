package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table1(json:JSONObject) {
    val portFolio        = json.string("portfolio")
    val aum              = json.string("aum").trim().replace(",", "").toFloat()
    val realized         = json.string("realized").trim().replace(",", "").toFloat()
    val target           = json.string("target").trim().replace(",", "").toFloat()
    val informationRatio = json.string("information_ratio").replace(",", "").trim().toFloat()
    val yield            = json.string("yield").trim().replace(",", "").toFloat()
    var varM             = json.string("var").trim().replace(",", "").toFloat()
   // val nav              = json.string("return_nav").trim().replace(",", "").toFloat()
   // val bmk              = json.string("return_bmk").trim().replace(",", "").toFloat()

    companion object {
        fun table1DropdownList():List<String>{
            return Arrays.asList("AUM (BN)", "Return - Nav", "Return - BMK", "IR", "Yield", "VAR")
        }
    }
}