package com.jarvis.app.activity

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import com.jarvis.app.R
import com.jarvis.app.adapter.NavigationSideMenuListAdapter
import com.jarvis.app.adapter.SideMenuExpandableAdapter
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.R.attr.keySet
import com.jarvis.app.utils.Util
import com.jarvis.app.utils.Util.GetDipsFromPixel
import android.util.DisplayMetrics




class MainActivity : AppCompatActivity() {
    private var toggle:ActionBarDrawerToggle? = null
    private var selectedNavIndex = 0
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
    }

    fun GetDipsFromPixel(pixels: Float): Int {
        // Get the screen's density scale
        val scale = resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 0.5f).toInt()
    }

    private fun setNavigationList(){
        val expandableListDetail: Map<String, ArrayList<String>> = StaticData.getData()
        val expandableListTitle: ArrayList<String> = ArrayList(StaticData.getData().keys)
        val adapter = SideMenuExpandableAdapter(this, expandableListTitle, expandableListDetail)
//        nav_view?.listNavView?.setIndicatorBounds(width - GetDipsFromPixel(this, 50F),
//            GetDipsFromPixel(this, 10F))
        nav_view?.listNavView?.setAdapter(adapter)
//        val adapter = NavigationSideMenuListAdapter(this@MainActivity, R.layout.row_nav_list, StaticData.titleAray())
//        nav_view?.listNavView?.adapter = adapter
//        nav_view?.listNavView?.onItemClickListener =
//                AdapterView.OnItemClickListener { parent, view, position, id ->
//                    selectedNavIndex = position
//                    if (selectedNavIndex == position){
//                        view.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_800))
//                        adapter.notifyDataSetChanged()
//                    }
//                    drawer_layout.closeDrawer(GravityCompat.START)
//                }
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
