package com.samuel.nasaimages.utils

import android.content.Context
import android.net.ConnectivityManager

object AppUtils {
    //Simple method to check if the device has an active internet connection
    fun hasInternet(context: Context): Boolean {

        val nInfo = (context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }

}