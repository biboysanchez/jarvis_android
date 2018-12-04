package com.jarvis.app.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jarvis.app.R

class SideMenuExpandableAdapter: BaseExpandableListAdapter {
    private var mContext:Context? = null
    private var titleList: ArrayList<String>? = ArrayList()
    private var dataList:HashMap<String, ArrayList<String>>? = HashMap()

    constructor(mContext: Context?, titleList: ArrayList<String>?, dataList: HashMap<String, ArrayList<String>>?) : super() {
        this.mContext   = mContext
        this.titleList  = titleList
        this.dataList   = dataList
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return dataList!![titleList?.get(listPosition)]!![expandedListPosition]
        }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val jobType = dataList!![titleList?.get(listPosition)]?.get(expandedListPosition)

        if (convertView == null) {
            val layoutInflater = mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.section_second, null)
        }

        val expandedListTextView = convertView?.findViewById<TextView>(R.id.rowSecondText)
        expandedListTextView?.text = jobType
        return convertView!!
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return  dataList!![titleList?.get(listPosition)]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return titleList!![listPosition]
    }

    override fun getGroupCount(): Int {
        return titleList!!.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater = mContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.section_parent, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.rowParentText)
        val imgDraw           = convertView.findViewById<ImageView>(R.id.imgDrawable)
        listTitleTextView.text = listTitle

        when (listPosition) {
            0 -> {
                imgDraw.setImageResource(R.drawable.ic_overview)
            }
            1 -> {
                imgDraw.setImageResource(R.drawable.ic_asset)
            }
            2 -> {
                imgDraw.setImageResource(R.drawable.ic_research)
            }
            3 -> {
                imgDraw.setImageResource(R.drawable.ic_performance)
            }
            4 -> {
                imgDraw.setImageResource(R.drawable.ic_portfolio)
            }
            5 -> {
                imgDraw.setImageResource(R.drawable.ic_strategic)
            }

            6 -> {
                imgDraw.setImageResource(R.drawable.ic_stress)
            }
        }
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}