package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.fragment.NewsSummaryFragment
import com.jarvis.app.model.News
import kotlinx.android.synthetic.main.row_comparition.view.*
import kotlinx.android.synthetic.main.row_news.view.*

class ComparationAdapter : RecyclerView.Adapter<ComparationAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<News>? = News.getNews()

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ComparationAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_comparition, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: ComparationAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val title = "${data!![i].title} - ${data!![i].date}"
            val mActivity = mContext as MainActivity

            if (i % 2 == 1){
                itemView.llRowComparation?.setBackgroundColor(Color.parseColor("#F4F9F9"))
            }else{
                itemView.llRowComparation?.setBackgroundColor(Color.parseColor("#EEF4F3"))
            }
        }
    }
}