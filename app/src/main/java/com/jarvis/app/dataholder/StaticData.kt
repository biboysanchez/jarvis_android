package com.jarvis.app.dataholder

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

}