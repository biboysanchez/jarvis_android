package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table3 (json:JSONObject) {
    val securities       = json.string("securities")
    val national         = json.string("notional")
    val avgCost          = json.int("avg_cost")
    val currentPrice     = json.int("current_price")
    val unrealized       = json.double("unrealized")
    val riskContribution = json.double("risk_contribution")

    companion object {
        fun table3DropDownList(): List<String>{
            return Arrays.asList("Notional", "Average cost", "Current price", "Unrealized", "Risked contribution")
        }
    }
}