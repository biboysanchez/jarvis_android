package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class TableRisk (json :JSONObject) {
    var id              = json.string("_id")
    var aum             = json.double("aum")
    var ytdAr           = json.double("ytd_ar")
    var yr3Ar           = json.double("3yr_ar")
    var targetAr        = json.double("target_ar")
    var ytdRr           = json.double("ytd_rr")
    var y3Rr            = json.double("ytd_rr")
    var targetRr        = json.double("target_rr")
    var portfolioSr     = json.double("portfolio_sr")
    var bmkSr           = json.double("bmk_sr")
    var varRc           = json.double("var_rc")
    var varPercentRc    = json.double("var_percent_rc")
    var infoRatio       = json.double("information_ratio")
    var beta            = json.double("beta")
    var group           = ""

    companion object {
        fun tableRiskDropDownList():List<String>{
            return Arrays.asList(
                "AUM",
                "Absolute Return - 3y Ar",
                "Absolute Return - Ytd Ar",
                "Absolute Return - Target AR",
                "Relative Return - 3y Ar",
                "Relative Return - Ytd Ar",
                "Relative Return - Target AR",
                "Sharpe Ratio - Portfolio SR",
                "Sharpe Ratio - Bmk. SR",
                "Risk Contribution - Var Rc",
                "Risk Contribution - Var % Rc",
                "Information Ratio",
                "Beta")
        }
    }
}