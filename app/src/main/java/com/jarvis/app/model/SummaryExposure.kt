package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.sql.Array
import java.util.*

class SummaryExposure (json:JSONObject) {
    var company         = json.string("company")
    var industry        = json.string("industry")
    var exposure        = json.string("highest_exposure")
    var netSensitivity  = json.string("net_sensitivity")

    companion object {
        fun getExposure():List<SummaryExposure>{
            val json1 = JSONObject().apply {
                put("company", "London Sumatra")
                put("industry", "Agriculture")
                put("highest_exposure", "Liabilities")
                put("net_sensitivity", "26%")
            }

            val json2 = JSONObject().apply {
                put("company", "Perusahan Gas")
                put("industry", "Basic Industry")
                put("highest_exposure", "Fuel Cost")
                put("net_sensitivity", "111%")
            }
            val json3 = JSONObject().apply {
                put("company", "PT Pertamina")
                put("industry", "Basic Industry")
                put("highest_exposure", "Coal Price")
                put("net_sensitivity", "-232%")
            }
            val json4 = JSONObject().apply {
                put("company", "Indofood Sukses")
                put("industry", "Consumer")
                put("highest_exposure", "Debt Cost")
                put("net_sensitivity", "-24%")
            }
            val json5 = JSONObject().apply {
                put("company", "Bond T")
                put("industry", "Mining")
                put("highest_exposure", "Fuel Cost")
                put("net_sensitivity", "46%")
            }

            return Arrays.asList(
                SummaryExposure(json1),
                SummaryExposure(json2),
                SummaryExposure(json3),
                SummaryExposure(json4),
                SummaryExposure(json5),
                SummaryExposure(json1),
                SummaryExposure(json2),
                SummaryExposure(json3),
                SummaryExposure(json4),
                SummaryExposure(json5))
        }
    }

}