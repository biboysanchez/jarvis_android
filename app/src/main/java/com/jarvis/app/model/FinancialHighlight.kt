package com.jarvis.app.model

class FinancialHighlight() {
    var year = ""
    var netSale = ""
    var cogs = ""

    companion object {
        fun getFinancialHightlights(): ArrayList<FinancialHighlight>{
            val array = ArrayList<FinancialHighlight>()
            array.add(FinancialHighlight().apply {
                year = "2016"
                netSale = "14,000"
                cogs = "30,000"
            })

            array.add(FinancialHighlight().apply {
                year = "2017"
                netSale = "14,000"
                cogs = "30,000"
            })

            array.add(FinancialHighlight().apply {
                year = "Q1 2018"
                netSale = "14,000"
                cogs = "30,000"
            })

            array.add(FinancialHighlight().apply {
                year = "Latest Q"
                netSale = "14,000"
                cogs = "30,000"
            })

            return array
        }
    }
}