package com.jarvis.app.model

class PerformanceSummary () {
    var portfolio = ""
    var aum = 0
    var returnNav = 0.0
    var returnBenchmark = 0.0
    var ir = 0.0
    var mYield = 0.0
    var mVar = 0.0

    companion object {
        fun getArrPerformanceSummary(): List<PerformanceSummary>{
            return arrayListOf(
                PerformanceSummary().apply {
                    portfolio = "Ablosolute 1"
                    aum = 10
                    returnNav = 0.0
                    returnBenchmark = -1.1
                    ir = 0.0
                    mYield = 0.0
                    mVar = 8.6
                }
            )
        }
    }
}