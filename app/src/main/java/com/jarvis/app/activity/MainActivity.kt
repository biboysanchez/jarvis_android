package com.jarvis.app.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jarvis.app.R
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.fragment.HomeFragment
import com.jarvis.app.utils.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import com.jarvis.app.adapter.HorizontalListAdapter
import com.jarvis.app.adapter.SideMenuAdapter


class MainActivity : AppCompatActivity() {
    private var toggle:ActionBarDrawerToggle? = null
    private var fm:FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setNavigationList()

        fm = supportFragmentManager

        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle!!)
        toggle?.syncState()

        addFragment(HomeFragment(), HomeFragment.TAG)


        spinnerWeek?.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(
            "All Week", "Week 1 - Sep 2018", "Week 2 - Sep 2018", "Week 3 - Sep 2018", "Week 4 - Sep 2018"))
        spinnerWeek?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        Util.changeTextColor(spinnerWeek, "#FFFFFF")

        setHorizontalScrollView()
    }

    private fun setHorizontalScrollView(){
        val btnLabels =Arrays.asList("BMA", "SMA", "JIWA", "MISG", "ASM", "BSA")
        rvHorizontal?.layoutManager =  GridLayoutManager(this,  1, GridLayoutManager.HORIZONTAL, false)
        rvHorizontal?.adapter = HorizontalListAdapter(this, btnLabels)
    }

    private fun setNavigationList(){
        nav_view?.listNavView?.layoutManager = LinearLayoutManager(this)
        nav_view?.listNavView?.adapter = SideMenuAdapter(this, StaticData.sideList())
    }

    fun addFragment(fragment:Fragment, tag:String){
        val ft = fm?.beginTransaction()
        ft?.addToBackStack(null)
        ft?.add(R.id.main_content, fragment, tag)
        ft?.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
