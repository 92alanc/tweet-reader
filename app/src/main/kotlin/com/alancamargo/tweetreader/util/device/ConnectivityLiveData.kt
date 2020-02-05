package com.alancamargo.tweetreader.util.device

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

class ConnectivityLiveData(
    private val connectivityHelper: ConnectivityHelper
) : LiveData<ConnectivityState>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(ConnectivityState(true))
        }

        override fun onLost(network: Network) {
            postValue(ConnectivityState(false))
        }

        override fun onUnavailable() {
            postValue(ConnectivityState(false))
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityHelper.registerNetworkCallback(networkCallback)
        val isConnected = connectivityHelper.isNetworkAvailable()

        postValue(ConnectivityState(isConnected))
    }

    override fun onInactive() {
        super.onInactive()
        connectivityHelper.unregisterNetworkCallback(networkCallback)
    }

}