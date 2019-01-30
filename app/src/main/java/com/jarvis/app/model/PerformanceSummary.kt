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
                    returnBenchmark = -1.1
                    mYield = 8.6
                    mVar = 8.6
                },
                PerformanceSummary().apply {
                    portfolio = "Konvensional"
                    aum = 842
                    returnBenchmark = -1.1
                    mYield = 8.0
                    mVar = 8.6
                },
                PerformanceSummary().apply {
                    portfolio = "Konvensional (USD)"
                    aum = 19
                    returnBenchmark = -1.1
                    mYield = 4.5
                    mVar = 8.6
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Balance Fund"
                    aum = 14
                    ir = 0.04
                    returnNav = -1.9
                    returnBenchmark = -3.1
                    mYield = 7.7
                    mVar = -6.3
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Equity Fund"
                    aum = 77
                    ir = 0.06
                    returnNav = -4.0
                    returnBenchmark = -5.1
                    mYield = 3.6
                    mVar = -14.9
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Equity Fund 2"
                    aum = 13194
                    ir = 0.23
                    returnNav = 1.5
                    returnBenchmark = -5.1
                    mYield = 0.7
                    mVar = -24.0
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Fund Rupiah"
                    aum = 14051
                    ir = 1.05
                    returnNav = 14.2
                    returnBenchmark = -1.1
                    mYield = 9.3
                    mVar = -15.6
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Fund Syariah Rupiah"
                    aum = 32
                    ir = 0.25
                    returnNav = 2.9
                    returnBenchmark = -1.1
                    mYield = 9.0
                    mVar = -3.4
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Jiwa Campuran Utama"
                    aum = 288
                    ir = 0.09
                    returnNav = -1.3
                    returnBenchmark = -3.1
                    mVar = -12.8
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Jiwa Fund Dollar"
                    aum = 408
                    ir = 0.15
                    returnNav = -2.8
                    returnBenchmark = -1.1
                    mYield = 7.9
                    mVar = -6.0
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Jiwa Investa Maxima Fund"
                    aum = 706
                    ir = 0.60
                    returnNav = 9.8
                    returnBenchmark = -3.1
                    mYield = 8.2
                    mVar = -6.8
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Stabil Fund"
                    aum = 4
                    ir = 0.04
                    returnNav = -2.1
                    returnBenchmark = -1.1
                    mYield = 7.4
                    mVar = -14.3
                },
                PerformanceSummary().apply {
                    portfolio = "Simas Tasyakur Fixed Fund"
                    aum = 4
                    ir = 0.37
                    returnNav = - 7.6
                    returnBenchmark = -1.1
                    mYield = 9.1
                    mVar = -10.5
                },
                PerformanceSummary().apply {
                    portfolio = "Syariah Pengelola"
                    aum = 20
                    returnBenchmark = -3.1
                    mYield = 7.3
                    mVar = 8.6
                },
                PerformanceSummary().apply {
                    portfolio = "Syariah Peserta"
                    aum = 51
                    returnBenchmark = -1.1
                    mYield = 7.3
                    mVar = 8.6
                },
                PerformanceSummary().apply {
                    portfolio = "Wealth Maxima Mixed"
                    aum = 14
                    ir = 0.49
                    returnNav = -8.9
                    returnBenchmark = -5.1
                    mYield = 3.1
                    mVar = -7.8
                }
            )
        }
    }
}