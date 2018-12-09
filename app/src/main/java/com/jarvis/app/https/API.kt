package com.jarvis.app.https

object API {
    private const val baseUrl = "http://jarvis-mobile-api.blipcom.com"
    const val login           = "$baseUrl/v1/mobile/login"
    const val pieAssetClass   = "$baseUrl/v1/api/get-asset-class-sector"
    const val pieCompany      = "$baseUrl/v1/api/get-company-range-duration"
}