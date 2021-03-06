package com.jarvis.app.model

class RiskReturn () {
    var corporateBonds  = 0f
    var governmentBonds = 0f
    var equity          = 0f
    var cash            = 0f

    fun get2017(): ArrayList<RiskReturn>{
        val arr = ArrayList<RiskReturn>()
        arr.add(RiskReturn().apply {
            governmentBonds = 28f
            corporateBonds  = 20f
            equity          = 33f
            cash            = 20f
        })

        arr.add(RiskReturn().apply {
            governmentBonds = 22f
            corporateBonds  = 24f
            equity          = 32f
            cash            = 24f
        })
        return arr
    }

    fun getExpectedReturn(): ArrayList<RiskReturn>{
        val arr = ArrayList<RiskReturn>()
        arr.add(RiskReturn().apply {
            corporateBonds  = 87f
            governmentBonds = 7.8f
            equity          = 2.9f
            cash            = 2.3f
        })

        arr.add(RiskReturn().apply {
            corporateBonds  = 84f
            governmentBonds = 5f
            equity          = 5f
            cash            = 6f
        })
        return arr
    }

    fun getExpectedRisk(): ArrayList<RiskReturn>{
        val arr = ArrayList<RiskReturn>()
        arr.add(RiskReturn().apply {
            corporateBonds  = 86.4f
            governmentBonds = 9.6f
            equity          = 4.0f
            cash            = 0.0f
        })

        arr.add(RiskReturn().apply {
            corporateBonds  = 82.0f
            governmentBonds = 10.0f
            equity          = 8.0f
            cash            = 0.0f
        })
        return arr
    }
}