package com.jarvis.app.activity

import android.animation.ValueAnimator
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.google.firebase.iid.FirebaseInstanceId
import com.jarvis.app.R
import com.jarvis.app.dataholder.StaticData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.jarvis.app.adapter.SideMenuAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.fragment.*
import com.jarvis.app.fragment.unused_old.*
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.UserViewModel
import com.jarvis.app.sessions.UserSession
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainActivity : AnimBaseActivity() {
    private lateinit var toggle:ActionBarDrawerToggle
    var fm:FragmentManager? = null
    var lastIndex = ""
    var viewModel:UserViewModel? = null
    var mainTitle           = "IC Decision Recap"
    var selectedCompany     = ""
    var selectedWeek        = ""
    var selectedCategory1   = ""
    var selectedCategory2   = ""
    var sortPerformance     = 0
    var sortTopTen          = 0
    var sortSecurity        = 0
    var sortAssetAllocation = 0
    var sortLease           = 0
    var sortRiskManagement  = 0
    var summaryList         = ArrayList<String>()
    var sortPerformanceAtt  = 0
    var mSession:UserSession? = null

    private var arrCompanyList: ArrayList<String> = ArrayList()

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

        mSession = UserSession(this)
        val token : String?

        try {
            if(mSession?.firebaseToken() != null && mSession?.firebaseToken() != ""){
                token = mSession?.firebaseToken()
                Log.d("firebase_token_stored",token)
            }
            else{
                token = FirebaseInstanceId.getInstance().token
                mSession?.storeFirebaseToken(token)
                Log.d("firebase_token_new",token)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        //title = mainTitle
        viewModel!!.title = mainTitle
        title = mainTitle
        addFragmentNoAnim(IcDecisionRecap(), IcDecisionRecap.TAG)
        isHideCompany(true)

        getCompanyList()

        Thread {
            setNavigationList()
        }.start()

//        if (!mSession!!.isActive){
//            if (!mSession!!.isShowFingerprintDialog()){
//                return
//            }
//            Handler().postDelayed({DialogUtil.showFingerPrintOption(this)},3000)
//        }
    }

    private fun setNavigationList(){
        nav_view?.listNavView?.layoutManager = LinearLayoutManager(this)
        nav_view?.listNavView?.adapter = SideMenuAdapter(this, StaticData.sideList())
        nav_view?.navMainHeader?.setOnClickListener {
            overridePendingTransitionEnter()
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
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

    fun getPage(page:String){
        drawer_layout.closeDrawer(GravityCompat.START)

//        if (lastIndex == page){
//            if (lastIndex == "00"){
//                toolbar.title = mainTitle
//            }
//            return
//        }

        lastIndex = page
        for (i in 0 until fm?.backStackEntryCount!!){
            if (i != 0){
                fm?.popBackStack()
            }
        }

        when (page) {
            "00" -> {
                viewModel!!.title = "IC Decision Recap"
                addFragmentNoAnim(IcDecisionRecap(), IcDecisionRecap.TAG)
                isHideCompany(true)
            }

            "01" -> {
                addFragmentNoAnim(MarketUpdateFragment(), MarketUpdateFragment.TAG)
                isHideCompany(true)
            }

            "02" -> {
                addFragmentNoAnim(WatchlistFragment(), WatchlistFragment.TAG)
                isHideCompany(true)
            }

            "1" -> {
                addFragmentNoAnim(PortfolioOverviewFragment(), PortfolioOverviewFragment.TAG)
                //viewModel!!.title = "Portfolio Overview"
                isHideCompany(false)
            }

            "4" -> {
                addFragmentNoAnim(PerformanceMeasurementFragment(), PerformanceMeasurementFragment.TAG)
                isHideCompany(true)
            }

            "6" ->{
                addFragmentNoAnim(StrategicAssetAllocationFragment(), StrategicAssetAllocationFragment.TAG)
                isHideCompany(true)
            }

            "20" -> {
                addFragmentNoAnim(CashFragment(), CashFragment.TAG)
                isHideCompany(true)
            }

            "21" -> {
                addFragmentNoAnim(InflowOutflowAnalysisFragment(), InflowOutflowAnalysisFragment.TAG)
                isHideCompany(true)
            }

            "30" -> {
                addFragmentNoAnim(CurrencyResearchFragment(), CurrencyResearchFragment.TAG)
                isHideCompany(true)
            }

            "31" -> {
                addFragmentNoAnim(SummaryExposureFragment(), SummaryExposureFragment.TAG)
                isHideCompany(true)
            }

            "32" -> {
                viewModel?.title = "Corporate Bond Scoring"
                addFragmentNoAnim(CorporateBondScoring(), CorporateBondScoring.TAG)
                isHideCompany(true)
            }

            "50" ->{
                //Fixed Income
                addFragmentNoAnim(FixedIncomeFragment(), FixedIncomeFragment.TAG)
            }

            "51" ->{
                addFragmentNoAnim(EquitiesFragment(), EquitiesFragment.TAG)
                //Equities
            }

            else ->{
                addFragmentNoAnim(BlankFragment(), BlankFragment.TAG)
                isHideCompany(true)
            }

        }
    }

    //Please do not delete for now
    //This is for old jarvis setup. just for backup and if I need previous data

    /*fun getPage(page:Int){
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
                //Default
                //Portfolio view
            }

            2 -> {
                addFragmentNoAnim(SummaryExposureFragment(), SummaryExposureFragment.TAG)
                viewModel!!.title = "Currency Sensitivity"
            }

            4 -> {
                addFragmentNoAnim(PortfolioConstructionFragment(), PortfolioConstructionFragment.TAG)
            }

            7 -> {
                addFragmentNoAnim(SettingsFragment(), SettingsFragment.TAG)
            }

            10 -> {
                addFragmentNoAnim(CashPositionFragment(), CashPositionFragment.TAG)
            }

            11 -> {
                addFragmentNoAnim(DurationMatchFragment(), DurationMatchFragment.TAG)
            }

            30 -> {
                addFragmentNoAnim(TimeSeriesFragment(), TimeSeriesFragment.TAG)
            }

            31 -> {
                addFragmentNoAnim(PerformanceDetailFragment(), PerformanceDetailFragment.TAG)
            }

            else -> {
                addFragmentNoAnim(BlankFragment(), BlankFragment.TAG)
            }
        }
    }*/

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

    fun isHideCompany(isHide:Boolean){
       // flCompany?.visibility = if (isHide) View.GONE else View.VISIBLE
        flSpinnerCompany?.visibility = if (isHide) View.GONE else View.VISIBLE
    }

    private fun getCompanyList(){
        ApiRequest.postNoUI(this, API.companyList, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(this@MainActivity, response)){
                    try {
                        arrCompanyList = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("portfolio_overview_company_list")
                        if (arr.length() > 0){
                            for (i in 0 until arr.length()){
                                Log.i(HomeFragment.TAG, "==> ${arr.getString(i)}")
                                arrCompanyList.add(arr.getString(i))
                            }

                            if (arrCompanyList.isNotEmpty()){
                                selectedCompany = arrCompanyList[0]
                                spinnerCompany?.adapter = ArrayAdapter(this@MainActivity, R.layout.support_simple_spinner_dropdown_item, arrCompanyList)
                                spinnerCompany?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                    }

                                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                        selectedCompany = arrCompanyList[position]
                                        //refreshAllDataWithCompany()

                                        try {
                                            (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                                        }catch (e: Exception){
                                            e.printStackTrace()
                                        }
                                    }
                                }
                            }
                           // rvHorizontal?.layoutManager = GridLayoutManager(this@MainActivity, 1, GridLayoutManager.HORIZONTAL, false)
                           // rvHorizontal?.adapter = HorizontalListAdapter(this@MainActivity, arrCompanyList)
                        }
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    private fun refreshAllDataWithCompany(){
        HomeFragment.instance?.refreshAll()
        PerformanceDetailFragment.instance?.refreshAll()
        TimeSeriesFragment.instance?.refreshAll()
        DurationMatchFragment.instance?.refreshAll()
        CashPositionFragment.instance?.refreshAll()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            Log.i("TAG", "count ${fm?.backStackEntryCount!!}")
            if (fm?.backStackEntryCount!! == 1){
                finish()
            }else if (fm?.backStackEntryCount!! == 2){
                if (lastIndex > "00"){
                    finish()
                }else{
                    super.onBackPressed()
                }
            }else{
                showBackButton(false)
                super.onBackPressed()
            }
        }
    }
}
