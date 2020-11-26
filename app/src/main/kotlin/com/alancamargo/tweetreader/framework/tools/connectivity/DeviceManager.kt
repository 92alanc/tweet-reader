package com.alancamargo.tweetreader.framework.tools.connectivity

interface DeviceManager {
    fun getConnectivityState(): ConnectivityLiveData
}