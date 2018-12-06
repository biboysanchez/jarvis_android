package com.jarvis.app.activity

import android.animation.ValueAnimator
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import com.jarvis.app.R
import com.jarvis.app.R.id.drawer_layout
import com.jarvis.app.R.id.toolbar
import com.jarvis.app.dataholder.StaticData
import com.jarvis.app.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.jarvis.app.adapter.SideMenuAdapter
import com.jarvis.app.fragment.BlankFragment
import com.jarvis.app.fragment.CashPositionFragment
import com.jarvis.app.model.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var toggle:ActionBarDrawerToggle
    var fm:FragmentManager? = null
    private var lastIndex = 0
    var viewModel:UserViewModel? = null
    var mainTitle = "Portfolio Overview"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fm = supportFragmentManager
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        title = mainTitle
        addFragmentNoAnim(HomeFragment(), HomeFragment.TAG)

        Thread {
            setNavigationList()
        }.start()
    }

    private fun setNavigationList(){
        nav_view?.listNavView?.layoutManager = LinearLayoutManager(this)
        nav_view?.listNavView?.adapter = SideMenuAdapter(this, StaticData.sideList())
    }

    fun addFragment(fragment:Fragment, tag:String){
        val ft = fm?.beginTransaction()
        ft?.setCustomAnimations(
            R.anim.slide_from_right,
            R.anim.slide_to_left,
            R.anim.slide_from_left,
            R.anim.slide_to_right)
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft?.addToBackStack(null)
        ft?.add(R.id.main_content, fragment, tag)
        ft?.commit()
    }

    private fun addFragmentNoAnim(fragment:Fragment, tag:String){
        val ft = fm?.beginTransaction()
        ft?.addToBackStack(tag)
        ft?.add(R.id.main_content, fragment, tag)
        ft?.commit()
    }

    fun getPage(page:Int){
        Log.i("MAin Activity", "stack count: ${fm?.backStackEntryCount!!}")
        drawer_layout.closeDrawer(GravityCompat.START)

        if (lastIndex == page){
            if (lastIndex == 0){
                toolbar.title = mainTitle
            }
            return
        }

        lastIndex = page

        for (i in 0 until fm?.backStackEntryCount!!){
            if (i != 0){
                fm?.popBackStack()
            }
        }

        when (page) {
            0 -> {

            }
            10 -> {
                addFragmentNoAnim(CashPositionFragment(), CashPositionFragment.TAG)
            }
            else -> {
                addFragmentNoAnim(BlankFragment(), BlankFragment.TAG)
            }
        }
    }

    fun removeAllBackStack() {
        fm!!.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * Set toolbar navigation icon
     * @param isShowBackButton if true the navigation button change to back else hamburger button
     */
    fun showBackButton(isShowBackButton:Boolean){
        val anim: ValueAnimator? = if (isShowBackButton){
            ValueAnimator.ofFloat(0F, 1F)
        }else{
            ValueAnimator.ofFloat(1F, 0F)
        }

        anim?.addUpdateListener { valueAnimator ->
            val slideOffset = valueAnimator.animatedValue as Float
            toggle.onDrawerSlide(drawer_layout, slideOffset)
        }
        anim?.interpolator = DecelerateInterpolator()
        anim?.duration = 400
        anim?.start()
        setDrawerState(!isShowBackButton)
    }

    /**
     * Control drawer behavior
     * @param isEnabled if true disable the side menu and unable to swipe else enable
     */
    private fun setDrawerState(isEnabled: Boolean) {
        if (isEnabled) {
            toggle.isDrawerIndicatorEnabled = isEnabled
            toggle.syncState()
            drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toolbar?.setNavigationOnClickListener {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        } else {
            drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toolbar?.setNavigationOnClickListener{ onBackPressed() }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            Log.i("TAG", "count ${fm?.backStackEntryCount!!}")
            if (fm?.backStackEntryCount!! == 1){
                finish()
            }else if (fm?.backStackEntryCount!! == 2){
                if (lastIndex > 0){
                    finish()
                }
            }else{
                showBackButton(false)
                super.onBackPressed()
            }
        }
    }

}
