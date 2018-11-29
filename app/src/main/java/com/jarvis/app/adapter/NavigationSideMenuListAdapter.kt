package com.jarvis.app.adapter

import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ArrayAdapter
import com.jarvis.app.R

class NavigationSideMenuListAdapter : ArrayAdapter<String> {
    private var mContext: Context
    private var titleArray:List<String> =ArrayList()
    private var selectedItem = 0

    constructor(context: Context, resource: Int, titleArray: List<String>) : super(
        context,
        resource,
        titleArray
    ) {
        this.mContext = context
        this.titleArray = titleArray
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.row_nav_list, parent, false)

        val name = listItem?.findViewById(R.id.tvRowTitle) as TextView
        name.text = titleArray[position]
        return listItem
    }
}