package com.alancamargo.tweetreader.util.device

interface DeviceManager {
    fun getConnectivityState(): ConnectivityLiveData
}