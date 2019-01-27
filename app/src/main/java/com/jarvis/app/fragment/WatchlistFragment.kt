package com.jarvis.app.fragment

import android.content.Context
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import kotlinx.android.synthetic.main.fragment_watchlist.*
import kotlinx.android.synthetic.main.pager_watchlist.*
import kotlinx.android.synthetic.main.pager_watchlist.view.*


class WatchlistFragment: BaseFragment() {
    private var mPagerAdapter:WatchlistViewPagerAdapter? = null

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

        setRadioGroup()
        setViewPager()
    }
    private fun setRadioGroup(){
        radioGroupWatchlist?.check(R.id.radioWatchlist)
        radioGroupWatchlist?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                R.id.radioWatchlist -> {
                    viewpagerWatchlist?.currentItem = 0
                    viewpagerWatchlist?.adapter = WatchlistViewPagerAdapter(context!!, 0)
                }

                R.id.radioRestricted -> {
                    viewpagerWatchlist?.currentItem = 1
                    viewpagerWatchlist?.adapter = WatchlistViewPagerAdapter(context!!, 1)
                }
            }
        }
    }

    private fun setViewPager(){
        viewpagerWatchlist?.adapter = WatchlistViewPagerAdapter(context!!, 0)
    }

    inner class WatchlistViewPagerAdapter(private val mContext: Context, var index:Int) : PagerAdapter() {
        private var titleList = arrayListOf("Watchlist", "Restricted Securities")

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val itemVIew = inflater.inflate(R.layout.pager_watchlist, collection, false) as ViewGroup
            itemVIew.tvWatchListHeaderTitle?.text = titleList[index]
            collection.addView(itemVIew)
            return itemVIew
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return 2
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}