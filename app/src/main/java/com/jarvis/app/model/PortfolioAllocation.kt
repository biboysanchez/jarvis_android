package com.jarvis.app.model

class PortfolioAllocation() {
    var asset       = ""
    var existing    = ""
    var simulation  = ""

    companion object {
        fun getPortfolioAllocation(): List<PortfolioAllocation>{
            return arrayListOf(
                PortfolioAllocation().apply {
                    asset = "Repo Equities"
                    existing = "20,3%"
                    simulation = "43,7%"
                },

                PortfolioAllocation().apply {
                    asset = "Equities"
                    existing = "20,3%"
                    simulation = "43,7%"
                },

                PortfolioAllocation().apply {
                    asset = "Corporate Bonds"
                    existing = "20,3%"
                    simulation = "43,7%"
                },

                PortfolioAllocation().apply {
                    asset = "Corporate Bonds \n- Proteksi"
                    existing = "10,0%"
                    simulation = "63,0%"
                },

                PortfolioAllocation().apply {
                    asset = "Gevernment Bonds"
                    existing = "20,3%"
                    simulation = "43,7%"
                },

                PortfolioAllocation().apply {
                    asset = "Gevernment Bonds \n- Statutory"
                    existing = "10,0%"
                    simulation = "54,9%"
                },

                PortfolioAllocation().apply {
                    asset = "Cash & CE"
                    existing = "20,3%"
                    simulation = "43,7%"
                }
            )
        }
    }
}