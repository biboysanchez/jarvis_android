package com.jarvis.app.model

import kotlin.collections.ArrayList

class Comparation() {
    var company = ""
    var industry = ""
    var lorem = ""
    var ipsum = ""
    var dolor = ""
    var isChecked = false

    companion object {
        fun getComparation(): ArrayList<Comparation> {
            val list = ArrayList<Comparation>()
            list.add(Comparation().apply {
                company = "Garuda"
                industry = "Airlines"
                lorem = "100"
                ipsum = "16 %"
                dolor = "50 Bn"
            })

            list.add(Comparation().apply {
                company = "Bond B"
                industry = "Mining"
                lorem = "100"
                ipsum = "16 %"
                dolor = "30 Bn"
            })

            list.add(Comparation().apply {
                company = "Bond T"
                industry = "Mining"
                lorem = "100"
                ipsum = "15 %"
                dolor = "0 Bn"
            })

            list.add(Comparation().apply {
                company = "Bond Y"
                industry = "Agriculture"
                lorem = "100"
                ipsum = "14 %"
                dolor = "30 Bn"
            })

            return list
        }

        fun getCompany(): ArrayList<Comparation> {
            val list = ArrayList<Comparation>()
            list.add(Comparation().apply {
                company = "PT Bank Pembangunan Daerah Jawa Barat dan Banten"
                industry = "Financials"
                lorem = "Rp 19,030.25"
                ipsum = "14.20%"
                dolor = "Rp 2,030.00"
            })

            list.add(Comparation().apply {
                company = "PT Waskita Beton Precast"
                industry = "Materials"
                lorem = "Rp 8,841.20"
                ipsum = "4.70%"
                dolor = "Rp 358.00"
            })

            list.add(Comparation().apply {
                company = "PT United Tractors"
                industry = "Energy"
                lorem = "Rp 121,622.04"
                ipsum = "22.50%"
                dolor = "Rp 33,000.00"
            })

            list.add(Comparation().apply {
                company = "PT Surya Citra Media"
                industry = "Communication Services"
                lorem = "Rp 27,492.02"
                ipsum = "33.90%"
                dolor = "Rp 1,875.00"
            })

            list.add(Comparation().apply {
                company = "PT Matahari Department Store"
                industry = "Consumer Discretionary"
                lorem = "Rp 19,265.76"
                ipsum = "79.80%"
                dolor = "Rp 6,925.00"
            })

            list.add(Comparation().apply {
                company = "PT Kalbe Farma"
                industry = "Health Care"
                lorem = "Rp 59,210.68"
                ipsum = "18.10%"
                dolor = "Rp 1,380.00"
            })

            list.add(Comparation().apply {
                company = "PT Gudang Garam"
                industry = "Consumer Staples"
                lorem = "Rp 141,400.21"
                ipsum = "19.30%"
                dolor = "Rp 74,050.00"
            })

            list.add(Comparation().apply {
                company = "PT Astra International"
                industry = "Consumer Discretionary"
                lorem = "Rp 29,883.55"
                ipsum = "17.20%"
                dolor = "Rp 7,350.00"
            })

            list.add(Comparation().apply {
                company = "PT. Chandra Asri Petrochemical"
                industry = "Materials"
                lorem = "Rp 88,505.88"
                ipsum = "14.30%"
                dolor = "Rp 4,920.00"
            })

            list.add(Comparation().apply {
                company = "PT. Indika Energy"
                industry = "Energy"
                lorem = "Rp 14,766.78"
                ipsum = "43.30%"
                dolor = "Rp 2,800.00"
            })

            return list
        }
    }
}
