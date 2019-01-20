package com.jarvis.app.model

class ExistingPosition() {
    var name = ""
    var costPrice = ""
    var marketPrice = ""

    companion object {
        fun getExistingPosition(): ArrayList<ExistingPosition>{
            val array = ArrayList<ExistingPosition>()
            array.add(ExistingPosition().apply {
                name = "Bond ABC"
                costPrice = "14,000"
                marketPrice = "30,000"
            })
            return array
        }
    }
}