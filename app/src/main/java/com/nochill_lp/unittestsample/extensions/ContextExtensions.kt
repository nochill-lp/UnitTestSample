package com.nochill_lp.unittestsample.extensions

import android.content.Context
import android.net.ConnectivityManager


fun Context.getUserConnectivityType(): String{

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when(cm.activeNetworkInfo?.type){
        ConnectivityManager.TYPE_WIFI -> "wifi"
        ConnectivityManager.TYPE_MOBILE -> "mobile"
        else -> "not_connected"
    }
}