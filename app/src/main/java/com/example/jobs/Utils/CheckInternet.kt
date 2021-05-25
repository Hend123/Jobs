package com.example.jobs.Utils

import android.content.Context
import android.net.ConnectivityManager


object CheckInternet {

    fun hasNetworkAvailable(context: Context): Boolean {
        var isConnected = false
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val networkInfo = manager?.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val wifiConnected = networkInfo.type == ConnectivityManager.TYPE_WIFI
            val mobileConnected = networkInfo.type == ConnectivityManager.TYPE_MOBILE
            if (wifiConnected || mobileConnected) {
                isConnected = true
            }
        } else {
            isConnected = false
        }
        return isConnected
    }

}

