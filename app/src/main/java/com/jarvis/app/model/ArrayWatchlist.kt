package com.jarvis.app.model

class ArrayWatchlist() {
    var stockName = ""
    var rationale = ""

    companion object {
        fun arrWatchList():List<ArrayWatchlist>{
            return arrayListOf(
                ArrayWatchlist().apply {
                    stockName = "INDF"
                    rationale = "Approved by the IC to be included in the watchlist. As of the data approval date. the security meets the constraints of YTM being more than 9%"
                },
                ArrayWatchlist().apply {
                    stockName = "JPFA"
                    rationale = "Approved by the IC to be included in the watchlist. As of the data approval date. the security meets the constraints of YTM being more than 9%"
                },
                ArrayWatchlist().apply {
                    stockName = "ASII"
                    rationale = "Approved by the IC to be included in the watchlist. As of the data approval date. the security meets the constraints of YTM being more than 9%"
                },
                ArrayWatchlist().apply {
                    stockName = "IMAS"
                    rationale = "Approved by the IC to be included in the watchlist. As of the data approval date. the security meets the constraints of YTM being more than 9%"
                },
                ArrayWatchlist().apply {
                    stockName = "EXCL"
                    rationale = "Approved by the IC to be included in the watchlist. As of the data approval date. the security meets the constraints of YTM being more than 9%"
                }
            )
        }

        fun arrRestricted():List<ArrayWatchlist>{
            return arrayListOf(
                ArrayWatchlist().apply {
                    stockName = "MEDC"
                    rationale = "IC has restricted the trading of this counter."
                },
                ArrayWatchlist().apply {
                    stockName = "DJTL"
                    rationale = "IC has restricted the trading of this counter."
                },
                ArrayWatchlist().apply {
                    stockName = "DOID"
                    rationale = "IC has restricted the trading of this counter."
                },
                ArrayWatchlist().apply {
                    stockName = "INDY"
                    rationale = "IC has restricted the trading of this counter."
                }
            )
        }
    }
}