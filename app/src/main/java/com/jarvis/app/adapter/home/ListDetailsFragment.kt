package com.jarvis.app.adapter.home
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.jarvis.app.R
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.model.Table1
import com.jarvis.app.model.Table2
import com.jarvis.app.model.Table3
import com.jarvis.app.model.UserViewModel
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

        setList()
        isShowBack(true)
    }

    private fun setList(){
        rvListDetails?.layoutManager = LinearLayoutManager(context)
        when(mActivity?.viewModel?.fragmentTag){
            "Performance Summary" -> {
                val adapter = PerformanceSummaryAdapter(context, mActivity?.viewModel?.list as ArrayList<Table1>?, 0, true)
                rvListDetails?.adapter = adapter
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        rvListDetails?.adapter = PerformanceSummaryAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table1>?,
                            position,
                            true
                        )
                    }
                }
            }

            "Securities Selection" -> {
                val adapter = SecuritySelectionAdapter(context, mActivity?.viewModel?.list as ArrayList<Table2>?, 0, true)
                rvListDetails?.adapter = adapter
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        rvListDetails?.adapter = SecuritySelectionAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table2>?,
                            position,
                            true
                        )
                    }
                }
            }

            //Top 10 Position
            else -> {
                val adapter = TopTenAdapter(context, mActivity?.viewModel?.list as ArrayList<Table3>?, 0, true)
                rvListDetails?.adapter = adapter
                spinnerList?.adapter = mActivity?.viewModel?.sAdapter
                spinnerList?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        (parent?.getChildAt(0) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
                        rvListDetails?.layoutManager = LinearLayoutManager(context)
                        rvListDetails?.adapter = TopTenAdapter(
                            context,
                            mActivity?.viewModel?.list as ArrayList<Table3>?,
                            position,
                            true
                        )
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