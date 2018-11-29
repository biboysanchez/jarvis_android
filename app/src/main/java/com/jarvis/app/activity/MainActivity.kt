package com.jarvis.app.activity

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.jarvis.app.R
import com.jarvis.app.adapter.NavigationSideMenuListAdapter
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.dataholder.chart.PieChart
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var toggle:ActionBarDrawerToggle? = null
    private var selectedNavIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setNavigationList()

        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle!!)
        toggle?.syncState()
        setPieChart()
    }

    private fun setNavigationList(){
        val adapter = NavigationSideMenuListAdapter(this@MainActivity, R.layout.row_nav_list, StaticData.titleAray())
        nav_view?.listNavView?.adapter = adapter
        nav_view?.listNavView?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    selectedNavIndex = position
                    if (selectedNavIndex == position){
                        view.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_800))
                        adapter.notifyDataSetChanged()
                    }
                    drawer_layout.closeDrawer(GravityCompat.START)
                }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setPieChart(){
        val pieChart = PieChart(this, pieChart)
        pieChart.data()
    }
}
