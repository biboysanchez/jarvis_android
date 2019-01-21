package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject
import java.sql.Array
import java.util.*

class SummaryExposure() {
    var company = ""
    var highExposure = ""
    var revenue = ""
    var income = ""
    var net = ""

    companion object {
        fun getExposure(): List<SummaryExposure> {
            return Arrays.asList(
                SummaryExposure().apply {
                    company = "Adaro\nEnergy"
                    highExposure = "Revenues - Export"
                    revenue = "1.7%"
                    income = "4.0%"
                    net = "7.2%"
                },

                SummaryExposure().apply {
                    company = "Alam\nSutera"
                    highExposure = "Non Operating Income (expense) -  Interest expenses"
                    revenue = "0.0%"
                    income = "0.0%"
                    net = "-1.0%"
                },

                SummaryExposure().apply {
                    company = "Aneka\nTambang"
                    highExposure = "Revenues -  Gold"
                    revenue = "2.9%"
                    income = "26.8%"
                    net = "78.2%"
                },

                SummaryExposure().apply {
                    company = "Astra\nInternational"
                    highExposure = "Revenues -  Sales of goods"
                    revenue = "0.5%"
                    income = "1.4%"
                    net = "1.4%"
                },

                SummaryExposure().apply {
                    company = "Bank\nCentral Asia"
                    highExposure = "Revenues -  Loans receivable"
                    revenue = "0.1%"
                    income = "0.1%"
                    net = "0.2%"
                },

                SummaryExposure().apply {
                    company = "Bank\nMandiri"
                    highExposure = "Revenues -  Loans"
                    revenue = "0.3%"
                    income = "1.1%"
                    net = "1.4%"
                },

                SummaryExposure().apply {
                    company = "Ciputra"
                    highExposure = "Non Operating Income (expense) -  Finance costs"
                    revenue = "0.0%"
                    income = "0.0%"
                    net = "-0.6%"
                },

                SummaryExposure().apply {
                    company = "Garuda\nIndonesia"
                    highExposure = "Revenues -  Passenger"
                    revenue = "1.2%"
                    income = "33.6%"
                    net = "15.9%"
                },
                SummaryExposure().apply {
                    company = "Indocement\nTunggal Prakarsa"
                    highExposure = "Operating Expense -  Raw materials used"
                    revenue = "0.0%"
                    income = "-0.9%"
                    net = "-0.9%"
                },

                SummaryExposure().apply {
                    company = "Indofood\nSukses Makmur"
                    highExposure = "Revenues -  Consumer Branded Product"
                    revenue = "0.5%"
                    income = "2.0%"
                    net = "3.3%"
                },

                SummaryExposure().apply {
                    company = "Indosat"
                    highExposure = "Revenues -  Interconnection services"
                    revenue = "0.2%"
                    income = "0.4%"
                    net = "-1.9%"
                },

                SummaryExposure().apply {
                    company = "Kalbe\nFarma"
                    highExposure = "Operating Expense -  Purchases"
                    revenue = "0.2%"
                    income = "-2.6%"
                    net = "-3.6%"
                },

                SummaryExposure().apply {
                    company = "Lippo\nKarawaci"
                    highExposure = "Operating Expense -  Healthcare"
                    revenue = "0.2%"
                    income = "-0.3%"
                    net = "-0.2%"
                },

                SummaryExposure().apply {
                    company = "London\nSumatra Plantation"
                    highExposure = "Revenues -  Export"
                    revenue = "0.1%"
                    income = "0.3%"
                    net = "0.5%"
                },

                SummaryExposure().apply {
                    company = "Mitra\nDiperkasa"
                    highExposure = "Operating Expense -  Merchandise inventories"
                    revenue = "0.0%"
                    income = "-0.5%"
                    net = "-13.7%"
                },

                SummaryExposure().apply {
                    company = "PT\nPertamina"
                    highExposure = "Operating Expense -  Direct materials"
                    revenue = "0.2%"
                    income = "-12.5%"
                    net = "-28.0%"
                },

                SummaryExposure().apply {
                    company = "Semen\nIndonesia"
                    highExposure = "Revenues -  Cement"
                    revenue = "0.1%"
                    income = "0.4%"
                    net = "0.6%"
                },

                SummaryExposure().apply {
                    company = "Telekomunikasi\nIndonesia"
                    highExposure = "Revenues -  Cellular internet and data"
                    revenue = "0.3%"
                    income = "0.3%"
                    net = "0.4%"
                },

                SummaryExposure().apply {
                    company = "Unilever"
                    highExposure = "Revenues -  Export"
                    revenue = "0.2%"
                    income = "0.0%"
                    net = "0.0%"
                },

                SummaryExposure().apply {
                    company = "United\nTractors"
                    highExposure = "Revenues -  Mining contracting"
                    revenue = "1.0%"
                    income = "3.0%"
                    net = "4.2%"
                }
            )
        }
    }
}