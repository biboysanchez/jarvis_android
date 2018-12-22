package com.jarvis.app.fragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.jarvis.app.R
import com.jarvis.app.adapter.LeaseAdapter
import com.jarvis.app.adapter.home.PerformanceSummaryAdapter
import com.jarvis.app.adapter.home.SecuritySelectionAdapter
import com.jarvis.app.adapter.home.TopTenAdapter
import com.jarvis.app.model.*
import com.jarvis.app.utils.CustomBottomSheet
import kotlinx.android.synthetic.main.fragment_list_all.*
import java.util.ArrayList

class ListDetailsFragment : BaseFragment() {

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

        sortAll(0)
        isShowBack(true)

        imgSortAll?.setOnClickListener {
            val bottomSheetDialogFragment = CustomBottomSheet()
            bottomSheetDialogFragment.selectedIndex = mActivity?.sortAll!!
            bottomSheetDialogFragment.mCalback = object : CustomBottomSheet.SorterCallback{
                override fun onSortSelected(selectedSorted: Int) {
                    sortAll(selectedSorted)
                }

            }
            bottomSheetDialogFragment.show(mActivity?.supportFragmentManager, bottomSheetDialogFragment.tag)

        }
    }

    private fun setRecyclerView(){
        rvListDetails?.layoutManager = LinearLayoutManager(context)
        spinnerList?.adapter = mActivity?.viewModel?.sAdapter
        spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))


            }
        }
    }

    private fun sortAll(sorter:Int){
        when(mActivity?.viewModel?.fragmentTag){
            "Performance Summary" -> {

                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)

                        val adapter = PerformanceSummaryAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table1>?,
                            position,
                            true
                        )

                        adapter.sortPerformance(sorter)
                        rvListDetails?.adapter = adapter

                    }
                }
            }

            "Securities Selection" -> {
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)

                        val adapter = SecuritySelectionAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table2>?,
                            position,
                            true
                        )

                        adapter.sortSecurity(sorter)
                        rvListDetails?.adapter = adapter
                    }
                }
            }

            "Lease Liquid Securities" -> {
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        rvListDetails?.adapter = LeaseAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table6>?,
                            position,
                            true
                        )

                        val adapter = LeaseAdapter(context,
                            mActivity?.viewModel?.list as ArrayList<Table6>?, 0, true)
                        adapter.sortLeaseLiquidity(sorter)
                        rvListDetails?.adapter = adapter
                    }
                }
            }

            //Top 10 Position
            else -> {
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        val adapter = TopTenAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table3>?,
                            position,
                            true
                        )

                        adapter.sortPerformance(sorter)
                        rvListDetails?.adapter = adapter

                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isShowBack(false)
    }
}