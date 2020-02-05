package com.alancamargo.tweetreader.util.device

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

class ConnectivityLiveData(
    private val connectivityHelper: ConnectivityHelper
) : LiveData<ConnectivityState>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(ConnectivityState.CONNECTED)
        }

        override fun onLost(network: Network) {
            postValue(ConnectivityState.DISCONNECTED)
        }

        override fun onUnavailable() {
            postValue(ConnectivityState.DISCONNECTED)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityHelper.registerNetworkCallback(networkCallback)
        val isConnected = connectivityHelper.isNetworkAvailable()

        val connectivityState = if (isConnected)
            ConnectivityState.CONNECTED
        else
            ConnectivityState.DISCONNECTED

        postValue(connectivityState)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityHelper.unregisterNetworkCallback(networkCallback)
    }

}