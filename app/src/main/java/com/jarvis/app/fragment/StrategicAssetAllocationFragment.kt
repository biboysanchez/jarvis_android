package com.jarvis.app.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.adapter.PortfolioAllocationAdapter
import kotlinx.android.synthetic.main.fragment_strategic_asset_allocation.*
import android.support.v4.view.PagerAdapter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.jarvis.app.adapter.SimpleColorLabelAdapter
import com.jarvis.app.custom.LabelFormatter
import com.jarvis.app.model.RiskReturn
import com.jarvis.app.utils.ColorUtil
import kotlinx.android.synthetic.main.paging_view.view.*
import java.util.ArrayList

class StrategicAssetAllocationFragment : BaseFragment() {
    var viewPagerAdapter: InnerViewPagerAdapter?  = null
    var viewPagerArray:ArrayList<ArrayList<RiskReturn>> = ArrayList()
    var viewpagerTitles = ArrayList<String>()

    companion object {
        val TAG = "StrategicAssetAllocationFragment"
        var instance:StrategicAssetAllocationFragment? = null
    }

    override fun setTitle(): String {
        return mActivity?.viewModel!!.title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater.inflate(R.layout.fragment_strategic_asset_allocation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpagerTitles = ArrayList()
        viewpagerTitles.add("Expected Return")
        viewpagerTitles.add("Expected Risk")

        tvCreateSimulation?.setOnClickListener {
            mActivity?.addFragment(RiskReturnSimulationFragment(), RiskReturnSimulationFragment.TAG)
        }

        setPortfolioAllocation()
        setViewPager()
        setRecyclerView()
    }

    private fun setPortfolioAllocation(){
        rvPortfolioAllocation?.layoutManager = LinearLayoutManager(context)
        rvPortfolioAllocation?.adapter = PortfolioAllocationAdapter(context)
    }

    private fun setRecyclerView(){
        rvRiskReturnProfile?.layoutManager = LinearLayoutManager(context)
        rvRiskReturnProfile?.adapter = SimpleColorLabelAdapter(context!!)
    }

    fun createNewSimulation(riskReturn: RiskReturn){
        val arr = ArrayList<RiskReturn>()
        arr.add(RiskReturn().getExpectedReturn()[0])
        arr.add(riskReturn)
        viewPagerAdapter?.addItem(arr)
        syncViewPager()
        viewPagerRisk?.currentItem = viewPagerArray.size-1
    }

    private fun setViewPager(){
        viewPagerAdapter = InnerViewPagerAdapter(context!!)
        viewPagerAdapter?.addItem(RiskReturn().getExpectedReturn())
        viewPagerAdapter?.addItem(RiskReturn().getExpectedRisk())
        syncViewPager()
    }

    private fun syncViewPager(){
        viewPagerRisk.offscreenPageLimit = viewPagerArray.size
        pageIndicatorView?.count = viewPagerArray.size
        viewPagerRisk?.adapter = viewPagerAdapter
    }

    inner class InnerViewPagerAdapter(private val mContext: Context) : PagerAdapter() {
        private var titleList = arrayListOf("Existing", "Simulation")

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val itemVIew = inflater.inflate(R.layout.paging_view, collection, false) as ViewGroup
            setStackBar(itemVIew, position)

            collection.addView(itemVIew)
            return itemVIew
        }

        fun addItem(arr: ArrayList<RiskReturn>){
            viewPagerArray.add(arr)
            notifyDataSetChanged()
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return viewPagerArray.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return viewpagerTitles[position]
        }

        private fun setStackBar(itemVIew: ViewGroup, position: Int) {
            itemVIew.paging_bar_chart?.description?.isEnabled = false
            itemVIew.paging_bar_chart?.setMaxVisibleValueCount(1)
            itemVIew.paging_bar_chart?.setPinchZoom(false)
            itemVIew.paging_bar_chart?.setDrawGridBackground(false)
            itemVIew.paging_bar_chart?.setDrawBarShadow(false)
            itemVIew.paging_bar_chart?.setDrawValueAboveBar(false)
            itemVIew.paging_bar_chart?.axisRight?.isEnabled = false
            itemVIew.paging_bar_chart?.isHighlightFullBarEnabled = false
            itemVIew.paging_bar_chart?.legend?.isEnabled = false
            itemVIew.paging_bar_chart?.xAxis?.setDrawGridLines(false)
            itemVIew.paging_bar_chart?.axisLeft?.setDrawLabels(true)

            // change the position of the y-labels
            val leftAxis = itemVIew.paging_bar_chart?.axisLeft
            //leftAxis?.valueFormatter = MyValueFormatter("K")
            leftAxis?.axisMinimum = 0f // this replaces setStartAtZero(true)


            val xLabels = itemVIew.paging_bar_chart?.xAxis
            xLabels?.position = XAxis.XAxisPosition.TOP
            val values = ArrayList<BarEntry>()
            for (a in 0 until viewPagerArray[position].size){
                val r = viewPagerArray[position][a]
                values.add(BarEntry(a.toFloat(), floatArrayOf(r.corporateBonds, r.governmentBonds, r.equity, r.cash)))
            }

            val xAxis = itemVIew.paging_bar_chart?.xAxis
            xAxis?.position = XAxis.XAxisPosition.BOTTOM
            xAxis?.valueFormatter = LabelFormatter(titleList)
            xAxis?.setLabelCount(titleList.size, false)

            val set1: BarDataSet
            if (itemVIew.paging_bar_chart?.data != null && itemVIew.paging_bar_chart?.data!!.dataSetCount > 0) {
                set1 = itemVIew.paging_bar_chart.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                itemVIew.paging_bar_chart?.data!!.notifyDataChanged()
                itemVIew.paging_bar_chart?.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "")
                set1.setDrawIcons(false)
                set1.colors = ColorUtil.strategicAssetColors()
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set1)

                val data = BarData(dataSets)
                data.setValueFormatter(StackedValueFormatter(false, "", 1))
                data.setValueTextColor(Color.WHITE)
                itemVIew.paging_bar_chart?.data = data
            }

            itemVIew.paging_bar_chart?.animateY(1300)
            itemVIew.paging_bar_chart?.setFitBars(true)
            itemVIew.paging_bar_chart?.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        instance = null
    }
}