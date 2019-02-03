package com.jarvis.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jarvis.app.R
import com.jarvis.app.model.CashMovement
import kotlinx.android.synthetic.main.row_cash_movement.view.*
import kotlinx.android.synthetic.main.text_cash_movement.view.*

class CashMovementAdapter : RecyclerView.Adapter<CashMovementAdapter.ViewHolder> {
    private var mContext: Context? = null
    private var data = CashMovement.arrCashMovement()


    constructor(mContext: Context?) : super() {
        this.mContext = mContext
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CashMovementAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_cash_movement, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: CashMovementAdapter.ViewHolder, p1: Int) {
        p0.bindItem(p1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(i: Int) {
            val cash = data[i]
            itemView.tvRowTitle?.text = cash.cash
            itemView.tvRowValue?.text = cash.value

            if (cash.isShow && cash.child.isNotEmpty()){
                itemView.llRowCashMovementView?.visibility = View.VISIBLE
            }else{
                itemView.llRowCashMovementView?.visibility = View.GONE
            }

            for (a in 0 until cash.child.size){
                val childData = cash.child[a]
                val v = LayoutInflater.from(mContext).inflate(R.layout.text_cash_movement, null)
                v.tvChildKey?.text = childData.key
                v.tvChildValue?.text = childData.value
                itemView.llRowCashMovementView?.addView(v)
            }
            itemView.tvRowTitle?.setOnClickListener {
                if (cash.isShow && cash.child.isNotEmpty()){
                    itemView.llRowCashMovementView?.visibility = View.GONE
                }else{
                    itemView.llRowCashMovementView?.visibility = View.VISIBLE
                }
                cash.isShow = !cash.isShow
            }
        }
    }
}