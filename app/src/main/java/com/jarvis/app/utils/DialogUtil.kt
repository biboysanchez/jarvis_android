package com.jarvis.app.utils

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.ValueKey
import com.jarvis.app.sessions.UserSession
import kotlinx.android.synthetic.main.dialog_fingerprint.view.*
import kotlinx.android.synthetic.main.dialog_list_detail.view.*
import kotlinx.android.synthetic.main.row_dialog.view.*

object DialogUtil {
    fun showCustomListDialog(context: Context, title:String, subTitle:String?,  list:List<ValueKey>?){
        val alert = AlertDialog.Builder(context)
        val dView = LayoutInflater.from(context).inflate(R.layout.dialog_list_detail, null)
        alert.setView(dView)
        dView.rvDialogList?.layoutManager = LinearLayoutManager(context)
        dView.rvDialogList?.adapter = DialogListAdapter(context, list)

        dView.tvDialogTitle?.text = title

        if (subTitle != null){
            dView.tvDialogSubTitle?.visibility = View.VISIBLE
            dView.tvDialogSubTitle?.text = subTitle
        }else{
            dView.tvDialogSubTitle?.visibility = View.GONE
        }

        val dialog = alert.create()

        dView.imgCloseDialog?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showCustomListDialog(context: Context, title:String, list:List<ValueKey>?){
        val alert = AlertDialog.Builder(context)
        val dView = LayoutInflater.from(context).inflate(R.layout.dialog_list_detail, null)
        alert.setView(dView)
        dView.rvDialogList?.layoutManager = LinearLayoutManager(context)
        dView.rvDialogList?.adapter = DialogListAdapter(context, list)
        dView.tvDialogTitle?.text = title

        val dialog = alert.create()

        dView.imgCloseDialog?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    class DialogListAdapter : RecyclerView.Adapter<DialogListAdapter.ViewHolder> {
        private var mContext: Context? = null
        private var data:List<ValueKey>? = ArrayList()

        constructor(mContext: Context?, data: List<ValueKey>?) : super() {
            this.mContext = mContext
            this.data = data
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DialogListAdapter.ViewHolder {
            val view = LayoutInflater.from(mContext).inflate(R.layout.row_dialog, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data?.size!!
        }

        override fun onBindViewHolder(p0: DialogListAdapter.ViewHolder, p1: Int) {
            p0.bindItem(p1)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItem(i: Int) {
                if (i % 2 == 1){
                    itemView.llRowDialog?.setBackgroundColor(Color.parseColor("#F4F9F9"))
                }else{
                    itemView.llRowDialog?.setBackgroundColor(Color.parseColor("#EEF4F3"))
                }

                itemView.tvDialogKey?.text = data!![i].key
                itemView.tvDialogValue?.text = data!![i].value
            }
        }
    }

    fun showFingerPrintOption(context: Context){
        val alert = AlertDialog.Builder(context)
        val aView = LayoutInflater.from(context).inflate(R.layout.dialog_fingerprint, null)
        alert.setView(aView)
        val dialog = alert.create()
        aView.btnAlertGotIt?.setOnClickListener {
            UserSession(context).donShow()
            dialog.dismiss()
        }
        dialog.show()
        dialog.setCancelable(false)
    }
}