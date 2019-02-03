package com.jarvis.app.model

class PerformanceMeasurement() {
    var category  = ""
    var portfolio = ""
    var aum       = ""
    var benchmark = ""
    var ytdAr     = ""
    var targetAr  = ""

    companion object {
        fun arrPerformanceMeasurements(): List<PerformanceMeasurement>{
            return arrayListOf(
                PerformanceMeasurement().apply {
                    category = "Balanced Fund"
                    portfolio = "Danamas Fleksi"
                    aum       = "424 B"
                    benchmark = "JCI"
                    ytdAr     = "-5%"
                    targetAr  = "11%"
                },
                PerformanceMeasurement().apply {
                    category = "Balanced Fund"
                    portfolio = "Simas Satu"
                    aum       = "424 B"
                    benchmark = "JCI"
                    ytdAr     = "8%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Balanced Fund"
                    portfolio = "Simas Satu Prima"
                    aum       = "65 B"
                    benchmark = "JCI"
                    ytdAr     = "-4%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Balanced Fund"
                    portfolio = "Simas Syariah Berkembang"
                    aum       = "23 B"
                    benchmark = "JCI"
                    ytdAr     = "12%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Equity Fund"
                    portfolio = "Danamas Saham"
                    aum       = "224 B"
                    benchmark = "JCI"
                    ytdAr     = "12%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Equity Fund"
                    portfolio = "Simas Saham Bertumbuh"
                    aum       = "49 B"
                    benchmark = "JCI"
                    ytdAr     = "-5%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Equity Fund"
                    portfolio = "Simas Saham Maxima"
                    aum       = "1957 B"
                    benchmark = "LQ-45"
                    ytdAr     = "5%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Equity Fund"
                    portfolio = "Simas Saham Unggulan"
                    aum       = "0"
                    benchmark = "LQ-45"
                    ytdAr     = "37%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Equity Fund"
                    portfolio = "Simas Syariah Unggulan"
                    aum       = "118 B"
                    benchmark = "JAK-ISL"
                    ytdAr     = "8%"
                    targetAr  = "10%"
                },
                PerformanceMeasurement().apply {
                    category = "Fixed Income Fund"
                    portfolio = "Danamas Dollar"
                    aum       = "0"
                    benchmark = "IBPA Total Return"
                    ytdAr     = "2%"
                    targetAr  = "4%"
                },
                PerformanceMeasurement().apply {
                    category = "Fixed Income Fund"
                    portfolio = "Danamas Instrumen Negara"
                    aum       =  "971 B"
                    benchmark = "IBPA Total Return"
                    ytdAr     = "-5%"
                    targetAr  = "11%"
                },
                PerformanceMeasurement().apply {
                    category = "Fixed Income Fund"
                    portfolio = "Danamas Mantap Plus"
                    aum       = "223 B"
                    benchmark = "IBPA Total Return"
                    ytdAr     = "8%"
                    targetAr  = "11%"
                },
                PerformanceMeasurement().apply {
                    category = "Fixed Income Fund"
                    portfolio = "Danamas Pasti"
                    aum       = "13 B"
                    benchmark = "IBPA Total Return"
                    ytdAr     = "12%"
                    targetAr  = "11%"
                },
                PerformanceMeasurement().apply {
                    category = "Fixed Income Fund"
                    portfolio = "Danamas Stabil"
                    aum       = "7269 B"
                    benchmark = "IBPA Total Return"
                    ytdAr     = "11%"
                    targetAr  = "11%"
                },
                PerformanceMeasurement().apply {
                    category = "Money Market Fund"
                    portfolio = "Danamas Rupiah Plus"
                    aum       = "0 "
                    benchmark = "ID MM Index"
                    ytdAr     = "7%"
                    targetAr  = "7%"
                }
            )
        }
    }
}