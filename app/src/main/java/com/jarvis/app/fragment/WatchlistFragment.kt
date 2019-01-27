package com.jarvis.app.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ArrayWatchlist
import com.jarvis.app.model.Watchlist
import kotlinx.android.synthetic.main.fragment_watchlist.*
import kotlinx.android.synthetic.main.row_2_column.view.*


class WatchlistFragment: BaseFragment() {

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    companion object {
        val TAG = "WatchlistFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvWatchlistPager?.layoutManager = LinearLayoutManager(context)
        setRecycler(0)
        setRadioGroup()

    }
    private fun setRadioGroup(){
        radioGroupWatchlist?.check(R.id.radioWatchlist)
        radioGroupWatchlist?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                R.id.radioWatchlist -> {
                    setRecycler(0)
                }

                R.id.radioRestricted -> {
                    setRecycler(1)
                }
            }
        }
    }

    private fun setRecycler(index:Int){
        tvWatchListHeaderTitle?.text = Watchlist.watchList()[index].title
        rvWatchlistPager?.adapter = WatchlistRecyclerAdapter(context, Watchlist.watchList()[index].arrWatchList)
    }

    inner class  WatchlistRecyclerAdapter: RecyclerView.Adapter<WatchlistRecyclerAdapter.ViewHolder> {
        var mContext: Context? = null
        var data: List<ArrayWatchlist>? = ArrayList()

        constructor(mContext: Context?, arrayList: List<ArrayWatchlist>) : super() {
            this.mContext = mContext
            this.data = arrayList
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WatchlistRecyclerAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_2_column, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data!!.size
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                itemView.tvRowColumn1?.text = data!![i].stockName
                itemView.tvRowColumn2?.text = data!![i].rationale
                if (i % 2 == 1){
                    itemView.llRow2Column?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                }else{
                    itemView.llRow2Column?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                }
            }
        }
    }
}