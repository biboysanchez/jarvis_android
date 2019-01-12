package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.activity.MainActivity
import com.jarvis.app.model.SideMenu
import kotlinx.android.synthetic.main.layout_top_10.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.row_home_list.view.*
import kotlinx.android.synthetic.main.section_parent.view.*
import kotlinx.android.synthetic.main.section_second.view.*

class SideMenuAdapter : RecyclerView.Adapter<SideMenuAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data: List<SideMenu>? = ArrayList()

    constructor(mContext: Context?, data: List<SideMenu>?) : super() {
        this.mContext = mContext
        this.data = data
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SideMenuAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.section_parent, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(p0: SideMenuAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val menu = data?.get(i)
            itemView.rowParentText?.text = menu?.name
            itemView.imgDrawable?.setImageResource(menu!!.icon)

            if (menu?.list!!.isEmpty()){
                itemView.imgArrow?.visibility = View.GONE
                itemView.rowSideMenu?.setOnClickListener {
                    clickedIndex(i.toString(), menu.name)
                }
            }else{
                itemView.imgArrow?.visibility = View.VISIBLE
                itemView.rowSideMenu?.setOnClickListener { it ->
                    if (menu.isExpanded){
                        menu.isExpanded = false
                        itemView.imgArrow?.setImageResource(R.drawable.ic_down_icon)
                        itemView.llChild?.visibility = View.GONE
                    }else{
                        menu.isExpanded = true
                        itemView.imgArrow?.setImageResource(R.drawable.ic_up_icon)
                        itemView.llChild?.visibility = View.VISIBLE

                        itemView.llChild?.removeAllViews()
                        for (a in 0 until menu.list!!.size){
                            val v = LayoutInflater.from(mContext).inflate(R.layout.section_second, null)
                            v.rowSecondText?.text = menu.list!![a]
                            v.rowSecondText?.setOnClickListener {
                                clickedIndex("$i$a", menu.list!![a])
                            }
                            itemView.llChild?.addView(v)
                        }
                    }
                }
            }
        }

        private fun clickedIndex(index:String, title:String){
            val mActivity = mContext as MainActivity
            mActivity.viewModel?.title = title

            Log.i("CLICKED", "row: $index")
            mActivity.getPage(index)

        }
    }
}