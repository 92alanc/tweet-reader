package com.alancamargo.tweetreader.util.device

class DeviceManagerImpl(connectivityHelper: ConnectivityHelper) : DeviceManager {

    private val liveData = ConnectivityLiveData(connectivityHelper)

    override fun getConnectivityState(): ConnectivityLiveData {
        return liveData
    }

}