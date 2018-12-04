package com.jarvis.app.dataholder

import android.graphics.Color
import com.github.mikephil.charting.data.PieData
import com.jarvis.app.model.Pie
import java.util.*
import kotlin.collections.ArrayList

object StaticData {
    fun getData(): HashMap<String, ArrayList<String>> {
        val expandableListDetail = HashMap<String, ArrayList<String>>()

        val portOverview = ArrayList<String>()

        val assetLiability = ArrayList<String>()
        assetLiability.add("Cash Position")
        assetLiability.add("Duration Match")

        val research = ArrayList<String>()

        val performanceMeasurement = ArrayList<String>()
        performanceMeasurement.add("Time Series")
        performanceMeasurement.add("Detail View")

        val portfolioConstruction = ArrayList<String>()
        val stratAssetAlloc = ArrayList<String>()
        val stressTest = ArrayList<String>()

        expandableListDetail["Portfolio Overview"]          = portOverview
        expandableListDetail["Asset Liability"]             = assetLiability
        expandableListDetail["Research"]                    = research
        expandableListDetail["Performance Measurement"]     = performanceMeasurement
        expandableListDetail["Portfolio Construction"]      = portfolioConstruction
        expandableListDetail["Strategic Asset Allocation"]  = stratAssetAlloc
        expandableListDetail["Stress Tess"]                 = stressTest
        return expandableListDetail
    }

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

}