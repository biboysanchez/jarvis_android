package com.jarvis.app.model

class CashMovement {
    var cash = ""
    var value = ""
    var child:List<ValueKey> = ArrayList()
    var isShow = true

    companion object {
        fun arrCashMovement():List<CashMovement>{
            return arrayListOf(
                CashMovement().apply {
                    cash = "Beginning Cash"
                    value = "500"
                },
                CashMovement().apply {
                    cash = "+ Inflow from Operations"
                    value = "2,713"
                    child = arrayListOf(
                        ValueKey("- Premium", "2,000"),
                        ValueKey("- Co-insurance & Re-insurance Claims", "500"),
                        ValueKey("- Commission & Receivables", "213")
                    )
                },
                CashMovement().apply {
                    cash = "+ Outflow from Operations"
                    value = "2,508"
                    child = arrayListOf(
                        ValueKey("- Re-insurance Premium", "1,200"),
                        ValueKey("- Claims", "500"),
                        ValueKey("- Commission", "300"),
                        ValueKey("- Expenses", "58")
                    )
                },
                CashMovement().apply {
                    cash = "+ Investment Inflow"
                    value = "-"
                    child = arrayListOf(
                        ValueKey("- Investment Proceed", "-"),
                        ValueKey("- Investment Redemption", "-")
                    )
                },
                CashMovement().apply {
                    cash = "+ Investment Outflow"
                    value = "1,002"
                    child = arrayListOf(
                        ValueKey("- Investment Placement", "1,002")
                    )
                },

                CashMovement().apply {
                    cash = "+ Others"
                    value = "-"
                },

                CashMovement().apply {
                    cash = "+ Cash Earmarked for Operational Purposes"
                    value = "-"
                },

                CashMovement().apply {
                    cash = "+ Cash from New Subscription"
                    value = "771"
                },

                CashMovement().apply {
                    cash = "Investment Outflow"
                    value = "893"
                }
            )
        }
    }
}