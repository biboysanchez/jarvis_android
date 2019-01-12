package com.jarvis.app.fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.volley.VolleyError
import com.jarvis.app.R
import com.jarvis.app.adapter.CashSummaryAdapter
import com.jarvis.app.adapter.LeaseAdapter
import com.jarvis.app.adapter.home.PerformanceSummaryAdapter
import com.jarvis.app.adapter.home.SecuritySelectionAdapter
import com.jarvis.app.adapter.home.TopTenAdapter
import com.jarvis.app.extension.arr
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import com.jarvis.app.https.API
import com.jarvis.app.https.ApiRequest
import com.jarvis.app.model.*
import com.jarvis.app.utils.CustomBottomSheet
import com.jarvis.app.utils.JSONUtil
import kotlinx.android.synthetic.main.fragment_list_all.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class ListDetailsFragment : BaseFragment() {
    var mSorter:Int = 0
    var mSelected:Int = 0
    var arrCashMovement:ArrayList<ValueKey>? = null
    var adapterCashMovement:CashSummaryAdapter? = null

    override fun setTitle(): String {
        return mActivity?.viewModel?.fragmentTag!!
    }

    companion object {
        const val TAG = "ListDetailsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        isShowBack(true)

        imgSortAll?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mSorter
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    mSorter = selectedSorted
                    sortAll(mSorter, selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun setRecyclerView(){
        if (mActivity?.viewModel?.fragmentTag == "Cash Movement Details"){
            spinnerList?.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item,
                mActivity?.summaryList)
            spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                    getCashMovement(mActivity?.summaryList!![position])
                }
            }
        }else{
            rvListDetails?.layoutManager = LinearLayoutManager(context)
            spinnerList?.adapter = mActivity?.viewModel?.sAdapter
            spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                    mSelected = position
                    sortAll(mSorter, mSelected)
                }
            }
        }

    }

    private fun sortAll(sorter:Int, selectedItem:Int){
        when(mActivity?.viewModel?.fragmentTag){
            "Performance Summary" -> {
                val adapter = PerformanceSummaryAdapter(
                    context,
                    mActivity?.viewModel?.list as ArrayList<Table1>?,
                    selectedItem,
                    true
                )

                adapter.sortPerformance(sorter)
                rvListDetails?.adapter = adapter
            }

            "Securities Selection" -> {
                val adapter = SecuritySelectionAdapter(
                    context,
                    mActivity?.viewModel?.list as ArrayList<Table2>?,
                    selectedItem,
                    true
                )

                adapter.sortSecurity(sorter)
                rvListDetails?.adapter = adapter
            }

            "Lease Liquid Securities" -> {
                val adapter = LeaseAdapter(context,
                    mActivity?.viewModel?.list as ArrayList<Table6>?,
                    selectedItem, true)
                adapter.sortLeaseLiquidity(sorter)
                rvListDetails?.adapter = adapter
            }

            "Top 10 Position" -> {
                val adapter = TopTenAdapter(
                    context,
                    mActivity?.viewModel?.list as ArrayList<Table3>?,
                    selectedItem,
                    true
                )

                adapter.sortPerformance(sorter)
                rvListDetails?.adapter = adapter
            }

            "Cash Movement Details" -> {
                Log.i(TAG, "Sorter: $sorter")
                adapterCashMovement?.sortCashSummary(sorter)
            }
        }
    }

    private fun getCashMovement(week:String){
        val params = HashMap<String, String>()
        params["company"]   = mActivity?.selectedCompany!!
        params["week"]      = week
        ApiRequest.postNoUI(context!!, API.cashMovement, params, object : ApiRequest.URLCallback{
            override fun didURLResponse(response: String) {
                if (JSONUtil.isSuccess(context!!, response)){
                    try {
                        arrCashMovement = ArrayList()
                        val arr = JSONObject(response).obj("message_data").arr("cashflow_list")
                        for (i in 0 until arr.length()){
                            val obj = arr.getJSONObject(i)
                            val keyValue = ValueKey(obj.string("label"), obj.string("value"))
                            arrCashMovement?.add(keyValue)
                        }

                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        adapterCashMovement = CashSummaryAdapter(context, arrCashMovement)
                        rvListDetails?.adapter = adapterCashMovement
                        adapterCashMovement?.sortCashSummary(mSorter)
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }

            override fun didURLFailed(error: VolleyError?) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isShowBack(false)
    }
}