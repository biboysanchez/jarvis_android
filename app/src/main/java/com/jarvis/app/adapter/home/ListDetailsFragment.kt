package com.jarvis.app.adapter.home
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.fragment.BaseFragment
import com.jarvis.app.model.Table1
import com.jarvis.app.model.UserViewModel
import kotlinx.android.synthetic.main.fragment_list_all.*
import java.util.ArrayList

class ListDetailsFragment : BaseFragment() {
    override fun setTitle(): String {
        return "Performance Summary"
    }
    private var viewModel:UserViewModel? = null
    companion object {
        const val TAG = "ListDetailsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        setList()
        isShowBack(true)
    }

    private fun setList(){
        rvListDetails?.layoutManager = LinearLayoutManager(context)
        when(viewModel?.fragmentTag){
            "Performance Summary" -> {
                val adapter = PerformanceSummaryAdapter(context, viewModel?.list as ArrayList<Table1>, 0, true)
                rvListDetails?.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isShowBack(false)
    }
}