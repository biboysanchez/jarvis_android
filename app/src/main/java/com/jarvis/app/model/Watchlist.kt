package com.jarvis.app.model

class Watchlist() {
    var arrWatchList: List<ArrayWatchlist> = ArrayList()
    var title = ""

    companion object {
        fun watchList(): List<Watchlist> {
            return arrayListOf(
                Watchlist().apply {
                    title = "Watchlist"
                    arrWatchList = ArrayWatchlist.arrWatchList()
                },
                Watchlist().apply {
                    title = "Restricted Securities"
                    arrWatchList = ArrayWatchlist.arrRestricted()
                }
            )
        }
    }
}