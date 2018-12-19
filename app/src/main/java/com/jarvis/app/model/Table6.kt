package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table6 (json:JSONObject) {
    var selectType      = json.string("select_type")
    var category        = json.int("category")
    var portfolio       = json.string("portfolio")
    var securities      = json.string("securities")
    var estDays         = json.double("est_days")
    var estTransactions = json.double("est_transactions")
    var bidAskCost      = json.double("bid_ask_cost")

    companion object {
        fun table6DropdownList():List<String>{
            return Arrays.asList("Est Days", "Est Transactions", "Bid Ask Cost")
        }
    }
}