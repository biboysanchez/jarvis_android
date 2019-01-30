package com.jarvis.app.model

class TopTen() {
    var security        = ""
    var natlValue       = 0.0
    var unrealized      = 0.0
    var avgCost         = 0.0
    var currentPrice    = 0.0

    companion object {
        fun arrTopTen():List<TopTen>{
            return arrayListOf(
                TopTen().apply {
                    security = "Mtn Indah Kiat Pulp & Paper I Tahun 2017"
                    natlValue = 400.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Indah Kiat Pulp & Paper Ix Tahun 2018"
                    natlValue = 550.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Lontar Papyrus Pulp & Paper Industry I Tahun 2017"
                    natlValue = 1000.00
                    unrealized = 0.12
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Lontar Papyrus Pulp & Paper Industry Ii Tahun 2017"
                    natlValue = 850.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Oki Pulp & Paper Mills I Tahun 2018"
                    natlValue = 580.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Pindo Deli Pulp & Paper Mills Iii Tahun 2018"
                    natlValue = 700.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Pindo Deli Pulp And Paper Mills I Tahun 2017"
                    natlValue = 500.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Mtn Sinar Mas Multifinance Iii Tahap I Tahun 2017"
                    natlValue = 500.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Obligasi Negara Seri Fr0075"
                    natlValue = 47.00
                    unrealized = 2.78
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Reksa Dana Simas Income Fund"
                    natlValue = 885.00
                    avgCost = 100.00
                },
                TopTen().apply {
                    security = "Reksa Dana Terproteksi Ascend Dana Proteksi Iii"
                    natlValue = 544.00
                    unrealized = 1.72
                    avgCost = 93.94
                }
            )
        }
    }
}