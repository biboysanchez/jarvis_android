package com.jarvis.app.dataholder

import android.graphics.Color
import android.view.Menu
import com.github.mikephil.charting.data.PieData
import com.jarvis.app.R
import com.jarvis.app.R.drawable.ic_research
import com.jarvis.app.model.IncomeStatement
import com.jarvis.app.model.Pie
import com.jarvis.app.model.SideMenu
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object StaticData {
    fun pieData() : List<Pie>{
        val pie0 = Pie(27,Color.parseColor("#18E4D1"), "Cash", 3198855)
        val pie1 = Pie(5,Color.parseColor("#239D92"), "Cash", 798855)
        val pie2 = Pie(18,Color.parseColor("#22ACC7"), "Cash", 2198855)
        val pie3 = Pie(4,Color.parseColor("#2C7A53"), "Equity", 698855)
        val pie4 = Pie(16,Color.parseColor("#239E60"), "Fixed Income", 1198855)
        val pie5 = Pie(4,Color.parseColor("#19C1E3"), "Fixed Income", 598855)
        val pie6 = Pie(14,Color.parseColor("#19E37E"), "Mutual Fund", 998855)
        val pie7 = Pie(3,Color.parseColor("#21C6B7"), "Mutual Fund", 498855)
        val pie8 = Pie(8,Color.parseColor("#2C7B74"), "Mutual Fund", 898855)
        val pie9 = Pie(2,Color.parseColor("#22C774"), "Mutual Fund", 398855)
        return Arrays.asList(
            pie0, pie1, pie2, pie3, pie4, pie5, pie6, pie7, pie8, pie9
        )
    }

    fun pieData2() : List<Pie>{
        val pie0 = Pie(27,Color.parseColor("#E3197C"), "Party Fund", 3198855)
        val pie1 = Pie(5,Color.parseColor("#9E2360"), "SM Group", 798855)
        val pie2 = Pie(18,Color.parseColor("#22C767"), "Party Fund", 2198855)
        val pie3 = Pie(4,Color.parseColor("#2C607A"), "Cash", 698855)
        val pie4 = Pie(16,Color.parseColor("#23759E"), "Cash", 1198855)
        val pie5 = Pie(4,Color.parseColor("#19E36E"), "3rd Party Fund", 598855)
        val pie6 = Pie(14,Color.parseColor("#199FE3"), "Government", 998855)
        val pie7 = Pie(3,Color.parseColor("#C72274"), "Mutual Fund", 498855)
        val pie8 = Pie(8,Color.parseColor("#7A2C53"), "Private", 898855)
        val pie9 = Pie(2,Color.parseColor("#2290C7"), "Mutual Fund", 398855)
        return Arrays.asList(
            pie0, pie1, pie2, pie3, pie4, pie5, pie6, pie7, pie8, pie9
        )
    }

    fun sideList():List<SideMenu>{
        val side0 = SideMenu("IC Decision Support", R.drawable.ic_overview, Arrays.asList("Decision Recap", "Market Update", "Watchlist"), false)
        val side1 = SideMenu("Portfolio Overview", R.drawable.ic_research, ArrayList(), false)
        val side2 = SideMenu("Cash Overview", R.drawable.ic_performance, Arrays.asList("Cash Position", "Inflow - Outflow Analysis"), false)
        val side3 = SideMenu("IC Decision Support", R.drawable.ic_research, Arrays.asList("Currency Research", "Summary Exposure", "Corporate Bond Score"), false)
        val side4 = SideMenu("Performance Measurement", R.drawable.ic_asset, ArrayList(), false)
        val side5 = SideMenu("Securities Selection", R.drawable.ic_stress, ArrayList(), false)
        val side6 = SideMenu("Strategic Asset Allocation", R.drawable.ic_strategic, ArrayList(), false)

//        val side1 = SideMenu("Asset Liability", R.drawable.ic_asset, Arrays.asList("Cash Position", "Duration Match"), false)
//        val side2 = SideMenu("Research", R.drawable.ic_research, ArrayList(), false)
//        val side3 = SideMenu("Performance Measurement", R.drawable.ic_performance, Arrays.asList("Time Series", "Detail View"), false)
//        val side4 = SideMenu("Portfolio Construction", R.drawable.ic_portfolio, ArrayList(), false)
//        val side5 = SideMenu("Strategic Asset Allocation", R.drawable.ic_strategic, ArrayList(), false)
//        val side6 = SideMenu("Stress Tess", R.drawable.ic_stress, ArrayList(), false)
//        val side7 = SideMenu("Settings", R.drawable.ic_fingerprint_settings, ArrayList(), false)
        return Arrays.asList(side0, side1, side2, side3, side4, side5, side6)
    }

    fun incomeStatements():ArrayList<IncomeStatement>{
        val list = ArrayList<IncomeStatement>()
        val json1 = JSONObject()
        json1.put("portfolio", "Revenue")
        json1.put("cost", 104.8)
        list.add(IncomeStatement(json1))

        val json2 = JSONObject()
        json2.put("portfolio", "COGS")
        json2.put("cost", -64.7)
        list.add(IncomeStatement(json2))

        val json3 = JSONObject()
        json3.put("portfolio", "Non Operating Income (Expense)")
        json3.put("cost", -9.0)
        list.add(IncomeStatement(json3))

        val json4 = JSONObject()
        json4.put("portfolio", "Operating Income (Expense)")
        json4.put("cost", -15.0)
        list.add(IncomeStatement(json3))

        val json5 = JSONObject()
        json5.put("portfolio", "Tax Obligation")
        json5.put("cost", -11.5)
        list.add(IncomeStatement(json5))

        val json6 = JSONObject()
        json6.put("portfolio", "Revenue")
        json6.put("cost", 13.8)
        list.add(IncomeStatement(json6))

        return list
    }
}