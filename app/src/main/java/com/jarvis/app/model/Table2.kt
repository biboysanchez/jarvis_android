package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table2 (json:JSONObject) {
    val period          = json.string("period")
    val weekId          = json.string("week_id")
    val company         = json.string("company")
    val action          = json.string("action")
    val amountIc        = json.int("amount_ic")
    val ttm             = json.int("ttm")
    val range           = json.string("range")
    val splitedRange    = json.string("range").substring(0, range.indexOf("-")).toFloat()
    val amountReal      = json.int("amount_real")
    val avgPrice        = json.double("avg_price")
    val percentage      = json.int("percentage")
    var strPercentage   = json.string("str_percentage")



    companion object {
        fun table2DropDownList() : List<String>{
            return Arrays.asList("Action", "Amount Ic", "Ttm", "Range", "Amount real", "Average Price", "Percentage")
        }
    }
}