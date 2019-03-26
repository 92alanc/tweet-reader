package com.alancamargo.tweetreader.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData

@RequiresApi(LOLLIPOP)
class ConnectivityMonitor : ConnectivityManager.NetworkCallback() {

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun enable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        isConnected.postValue(true)
        Log.d(javaClass.simpleName, "Connected")
    }

    override fun onLost(network: Network?) {
        super.onLost(network)
        isConnected.postValue(false)
        Log.d(javaClass.simpleName, "Disconnected")
    }

    companion object {
        var isConnected = MutableLiveData<Boolean>().apply { postValue(false) }
    }

}