package com.alancamargo.tweetreader.framework.tools.connectivity

import android.net.ConnectivityManager

interface ConnectivityHelper {
    fun isNetworkAvailable(): Boolean
    fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback)
    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback)
}
