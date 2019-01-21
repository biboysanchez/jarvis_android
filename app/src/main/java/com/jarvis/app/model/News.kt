package com.jarvis.app.model

class News {
    var title  = ""
    var date  = "18/09/2018"
    var image = ""

    companion object {
        fun getNews():ArrayList<News>{
            val arr = ArrayList<News>()
            arr.add(News().apply { title = "Kementrian Badan Usaha Milik Negara (BUMN) berharap pembentukan dua holding selesai di akhir tahun 2018."})
            return arr
        }
    }
}