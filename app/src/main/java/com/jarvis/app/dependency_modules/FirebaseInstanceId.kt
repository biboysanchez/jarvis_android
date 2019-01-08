package com.bingwit.consumer.dependency_modules

import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.internal.FirebaseAppHelper.getToken



class FirebaseInstanceId : FirebaseInstanceIdService(){

    override fun onTokenRefresh() {
        super.onTokenRefresh()

    }
}