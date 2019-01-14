package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class BondScore (json:JSONObject) {
    var company         = json.string("company")
    var industry        = json.string("industry")
    var exposure        = json.string("exposure")
    var netSensitivity  = json.string("net_sensitivity")

    companion object {
        fun getHighestScore(): List<BondScore> {
            val json1 = JSONObject().apply {
                put("company", "Bond V")
                put("industry", "Mining")
                put("exposure", "0 Bn")
                put("net_sensitivity", "16")
            }

            val json2 = JSONObject().apply {
                put("company", "Bond B")
                put("industry", "Mining")
                put("exposure", "50 Bn")
                put("net_sensitivity", "16")
            }
            val json3 = JSONObject().apply {
                put("company", "Bond T")
                put("industry", "Mining")
                put("exposure", "50 Bn")
                put("net_sensitivity", "15")
            }

            val json4 = JSONObject().apply {
                put("company", "Bond Y")
                put("industry", "Agriculture")
                put("exposure", "50 Bn")
                put("net_sensitivity", "13")
            }
            val json5 = JSONObject().apply {
                put("company", "Bond D")
                put("industry", "Mining")
                put("exposure", "50 Bn")
                put("net_sensitivity", "13")
            }

            val json6 = JSONObject().apply {
                put("company", "Bond N")
                put("industry", "Consumer Goods")
                put("exposure", "50 Bn")
                put("net_sensitivity", "12")
            }

            val json7 = JSONObject().apply {
                put("company", "Bond J")
                put("industry", "Mining")
                put("exposure", "50 Bn")
                put("net_sensitivity", "10")
            }

            val json8 = JSONObject().apply {
                put("company", "Bond L")
                put("industry", "Agriculture")
                put("exposure", "50 Bn")
                put("net_sensitivity", "10")
            }

            val json9 = JSONObject().apply {
                put("company", "Bond A")
                put("industry", "Mining")
                put("exposure", "50 Bn")
                put("net_sensitivity", "10")
            }

            val json10 = JSONObject().apply {
                put("company", "Bond K")
                put("industry", "Agriculture")
                put("exposure", "50 Bn")
                put("net_sensitivity", "10")
            }

            return Arrays.asList(
                BondScore(json1),
                BondScore(json2),
                BondScore(json3),
                BondScore(json4),
                BondScore(json5),
                BondScore(json6),
                BondScore(json7),
                BondScore(json8),
                BondScore(json9),
                BondScore(json10)
            )
        }


        fun getWorstScore():List<BondScore>{
            val json1 = JSONObject().apply {
                put("company", "Bond C")
                put("industry", "Infrastructure")
                put("exposure", "0 Bn")
                put("net_sensitivity", "-3")
            }

            val json2 = JSONObject().apply {
                put("company", "Bond B")
                put("industry", "Mining")
                put("exposure", "0 Bn")
                put("net_sensitivity", "0")
            }
            val json3 = JSONObject().apply {
                put("company", "Bond T")
                put("industry", "Financial")
                put("exposure", "0 Bn")
                put("net_sensitivity", "0")
            }

            val json4 = JSONObject().apply {
                put("company", "Bond Y")
                put("industry", "Agriculture")
                put("exposure", "0 Bn")
                put("net_sensitivity", "1")
            }
            val json5 = JSONObject().apply {
                put("company", "Bond S")
                put("industry", "Mining")
                put("exposure", "0 Bn")
                put("net_sensitivity", "1")
            }

            val json6 = JSONObject().apply {
                put("company", "Bond N")
                put("industry", "Consumer Goods")
                put("exposure", "0 Bn")
                put("net_sensitivity", "1")
            }

            val json7 = JSONObject().apply {
                put("company", "Bond M")
                put("industry", "Mining")
                put("exposure", "0 Bn")
                put("net_sensitivity", "3")
            }

            val json8 = JSONObject().apply {
                put("company", "Bond L")
                put("industry", "Agriculture")
                put("exposure", "0 Bn")
                put("net_sensitivity", "4")
            }

            val json9 = JSONObject().apply {
                put("company", "Bond A")
                put("industry", "Mining")
                put("exposure", "0 Bn")
                put("net_sensitivity", "4")
            }

            val json10 = JSONObject().apply {
                put("company", "Bond K")
                put("industry", "Agriculture")
                put("exposure", "0 Bn")
                put("net_sensitivity", "5")
            }

            return Arrays.asList(
                BondScore(json1),
                BondScore(json2),
                BondScore(json3),
                BondScore(json4),
                BondScore(json5),
                BondScore(json6),
                BondScore(json7),
                BondScore(json8),
                BondScore(json9),
                BondScore(json10))
        }
    }

}