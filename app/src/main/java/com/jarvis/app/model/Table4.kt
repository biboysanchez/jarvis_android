package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table4 (json:JSONObject) {
    val decisionDate        = json.string("decision_date")
    val portfolio           = json.string("portfolio")
    val assetClassSector    = json.string("asset_class_sector")
    val action              = json.string("action")

    companion object {
        fun table3DropDownList():List<String>{
            return Arrays.asList("Underweight", "Overweight")
        }
    }
}