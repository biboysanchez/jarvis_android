package com.jarvis.app.model

import java.util.*

class KeyIndicator() {
    var title = ""
    var value = ""
    var arrKeyValue: List<ValueKey>? = ArrayList()

    companion object {
        fun getKeyIndicators():ArrayList<KeyIndicator>{
            val arr = ArrayList<KeyIndicator>()
            val kI0 = KeyIndicator().apply {
                title = "Earning & Profitability"
                value = "61"
                arrKeyValue = Arrays.asList(
                    ValueKey("Earnings", "23"),
                    ValueKey("Financial results", "-10"),
                    ValueKey("Organic growth", "15"),
                    ValueKey("Financial difficulties", "21")
                )
            }

            val kI1 = KeyIndicator().apply {
                title = "Merger & Acquisition"
                value = "59"
                arrKeyValue = Arrays.asList(
                    ValueKey("Acquisition", "23"),
                    ValueKey("Merger", "-10"),
                    ValueKey("Spin off", "15"),
                    ValueKey("Divestitures", "21")
                )
            }

            val kI2 = KeyIndicator().apply {
                title = "Financial Policy"
                value = "57"
                arrKeyValue = Arrays.asList(
                    ValueKey("Equity", "23"),
                    ValueKey("Debt", "-10"),
                    ValueKey("Dividends", "15")
                )
            }

            val kI3 = KeyIndicator().apply {
                title = "Management & Government"
                value = "63"
                arrKeyValue = Arrays.asList(
                    ValueKey("Governance charge", "23"),
                    ValueKey("Strategy", "-10"),
                    ValueKey("Partnership", "15")
                )
            }

            val kI4 = KeyIndicator().apply {
                title = "Reputation"
                value = "-8"
                arrKeyValue = Arrays.asList(
                    ValueKey("Fraud", "23"),
                    ValueKey("Corruption", "-10"),
                    ValueKey("Legal Issue", "15")
                )
            }

            val kI5 = KeyIndicator().apply {
                title = "Rating & Analyst Recommendations"
                value = "61"
                arrKeyValue = Arrays.asList(
                    ValueKey("Analyst recommendations", "23"),
                    ValueKey("Credit rating", "-10"),
                    ValueKey("Market update", "15")
                )
            }

            arr.add(kI0)
            arr.add(kI1)
            arr.add(kI2)
            arr.add(kI3)
            arr.add(kI4)
            arr.add(kI5)
            return arr
        }
    }
}