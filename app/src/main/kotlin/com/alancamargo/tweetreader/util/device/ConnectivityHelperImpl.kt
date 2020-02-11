package com.alancamargo.tweetreader.util.device

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N

class ConnectivityHelperImpl(private val context: Context) : ConnectivityHelper {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    // TODO: find replacement for deprecated properties
    override fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    override fun registerNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        if (SDK_INT >= N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    override fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}