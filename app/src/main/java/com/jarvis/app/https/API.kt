package com.jarvis.app.https

object API {
    private const val baseUrl       = "http://jarvis-mobile-api.blipcom.com/v1"
    const val login                 = "$baseUrl/mobile/login"
    const val pieAssetClass         = "$baseUrl/api/get-asset-class-sector"
    const val pieCompany            = "$baseUrl/api/get-company-range-duration"
    const val portfolioDropDownList = "$baseUrl/api/get-performance-overview-category"
    const val companyList           = "$baseUrl/api/get-performance-overview-company"
}