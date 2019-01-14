package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.News
import kotlinx.android.synthetic.main.row_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<News>? = News.getNews()

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_news, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: NewsAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val title = "${data!![i].title} - ${data!![i].date}"
            itemView.tvRowArticle?.text = title

            itemView.llRowNews?.setOnClickListener {

            }

            if (i % 2 == 1){
                itemView.llRowNews?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowNews?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}