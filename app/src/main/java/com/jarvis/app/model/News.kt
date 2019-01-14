package com.jarvis.app.model

class News {
    var title  = ""
    var date  = "18/09/2018"
    var image = ""

    companion object {
        fun getNews():ArrayList<News>{
            val arr = ArrayList<News>()
            arr.add(News().apply { title = "Rating notification Pefindo"})
            arr.add(News().apply { title = "Analyst Report"})
            arr.add(News().apply { title = "Article FT"})
            arr.add(News().apply { title = "Article Jakarta Post"})
            arr.add(News().apply { title = "Article Jakarta Globe"})
            arr.add(News().apply { title = "Article Ja"})
            arr.add(News().apply { title = "Rating Notification Pefindo"})
            return arr
        }
    }
}