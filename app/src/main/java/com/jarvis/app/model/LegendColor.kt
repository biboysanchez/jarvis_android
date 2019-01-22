package com.jarvis.app.model
import android.graphics.Color
class LegendColor() {
    var color = 0
    var label = ""

    companion object {
        fun legend(): List<LegendColor> {
            return arrayListOf(
                LegendColor().apply {
                    color = Color.parseColor("#f1b52c")
                    label = "Cash"
                },

                LegendColor().apply {
                    color = Color.parseColor("#e6771a")
                    label = "Equity"
                },

                LegendColor().apply {
                    color = Color.parseColor("#b2ddd7")
                    label = "Government Bonds"
                },

                LegendColor().apply {
                    color = Color.parseColor("#21c4b6")
                    label = "Corporate Bonds"
                }
            )
        }
    }
}