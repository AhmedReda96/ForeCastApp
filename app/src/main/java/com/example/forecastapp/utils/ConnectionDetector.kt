package com.example.forecastapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkInfo.State.CONNECTED

class ConnectionDetector(private val _context: Context) {
    val isConnectingToInternet: Boolean
        get() {
            val connectivity =
                _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null) for (i in info.indices) if (info[i].state == CONNECTED) {
                    return true
                }
            }
            return false
        }

}