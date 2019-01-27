package com.jarvis.app.model

import com.jarvis.app.R

class InvestmentDecision() {
    var category = ""
    var deadline = ""
    var status   = "completed"
    var img   = R.drawable.ic_checked

    companion object {
        fun investments():List<InvestmentDecision>{
            return arrayListOf(
                InvestmentDecision().apply {
                    category = "Investment Strategy"
                    deadline = "Ongoing effort (No issue expected)"
                    status   = "completed"
                    img      = R.drawable.ic_checked
                },
                InvestmentDecision().apply {
                    category = "Portfolio Management"
                    deadline = "Completed"
                    status   = "on-going-with-challenge"
                    img      = R.drawable.ic_warning
                },
                InvestmentDecision().apply {
                    category = "Portfolio Monitoring"
                    deadline = "To revisit both decision by end of January"
                    status   = "on-going"
                    img      = R.drawable.ic_more
                },
                InvestmentDecision().apply {
                    category = "Risk Management"
                    deadline = "To revisit both decision by end of January"
                    status   = "on-going"
                    img      = R.drawable.ic_failed
                }
            )
        }
    }
}