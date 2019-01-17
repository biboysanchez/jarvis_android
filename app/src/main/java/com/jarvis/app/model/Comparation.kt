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
                company = "Company A"
                industry = "Airlines"
                lorem = "100"
                ipsum = "16 %"
                dolor = "50 Bn"
            })

            list.add(Comparation().apply {
                company = "Company B"
                industry = "Mining"
                lorem = "100"
                ipsum = "16 %"
                dolor = "30 Bn"
            })

            list.add(Comparation().apply {
                company = "Company C"
                industry = "Mining"
                lorem = "100"
                ipsum = "15 %"
                dolor = "0 Bn"
            })

            list.add(Comparation().apply {
                company = "Company D"
                industry = "Agriculture"
                lorem = "100"
                ipsum = "14 %"
                dolor = "30 Bn"
            })

            list.add(Comparation().apply {
                company = "Company E"
                industry = "Airlines"
                lorem = "100"
                ipsum = "16 %"
                dolor = "50 Bn"
            })

            list.add(Comparation().apply {
                company = "Company F"
                industry = "Mining"
                lorem = "100"
                ipsum = "16 %"
                dolor = "30 Bn"
            })

            list.add(Comparation().apply {
                company = "Company G"
                industry = "Mining"
                lorem = "100"
                ipsum = "15 %"
                dolor = "0 Bn"
            })

            list.add(Comparation().apply {
                company = "Company H"
                industry = "Agriculture"
                lorem = "100"
                ipsum = "14 %"
                dolor = "30 Bn"
            })

            return list
        }
    }
}
