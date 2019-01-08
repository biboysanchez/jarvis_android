package com.jarvis.app.https

object API {
    private const val baseUrl       = "http://jarvis-mobile-api.blipcom.com/v1"
    const val login                 = "$baseUrl/mobile/login"
    const val pieAssetClass         = "$baseUrl/api/get-asset-class-sector"
    const val pieCompany            = "$baseUrl/api/get-company-range-duration"
    const val portfolioDropDownList = "$baseUrl/api/get-performance-overview-category"
    const val companyList           = "$baseUrl/api/get-performance-overview-company"
    const val weekList              = "$baseUrl/api/get-performance-overview-week-id"
    const val performanceSummary    = "$baseUrl/api/get-performance-summary"
    const val securitySelection     = "$baseUrl/api/get-securities-selection"
    const val topTen                = "$baseUrl/api/get-top-ten"
    const val assetAllocation       = "$baseUrl/api/get-asset-allocation"

    //Time Series
    const val returnVsBenchmark     = "$baseUrl/api/get-pm-return-vs-benchmark"
    const val portfolio             = "$baseUrl/api/get-pm-portfolio-holding"
    const val portfolioDropdown     = "$baseUrl/api/get-pm-portfolio"
    const val performanceAttribute  = "$baseUrl/api/get-pm-perf-attrib"

    //Detail View
    const val liquidityProfile      = "$baseUrl/api/get-pm-liquidity-profile"
    const val riskMeasurement       = "$baseUrl/api/get-pm-performance-and-risk"
    const val leasedLiquid          = "$baseUrl/api/get-pm-lease-liquidity"

    //Asset and Liabilities
    const val assetMatching        = "$baseUrl/api/get-asset-liability-matching"
    const val cumulativeSurplus    = "$baseUrl/api/get-al-cumu-surplus-gap"
    const val assets               = "$baseUrl/api/get-al-asset-breakdown"
    const val liabilities          = "$baseUrl/api/get-al-asset-liability-breakdown"

    const val cashPosition         = "$baseUrl/api/get-asset-liability-cashflow-summary"
    const val cashPlacement        = "$baseUrl/api/get-asset-liability-cash-placement-yield"
    const val cashMovementWeek     = "$baseUrl/api/get-asset-liability-cash-movement-week"
    const val cashMovement         = "$baseUrl/api/get-asset-liability-cash-movement"

    const val SUBSCRIBE             = "$baseUrl/notification/subscribe"
}
