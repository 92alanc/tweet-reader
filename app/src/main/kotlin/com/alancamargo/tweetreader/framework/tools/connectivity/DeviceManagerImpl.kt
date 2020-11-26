package com.alancamargo.tweetreader.framework.tools.connectivity

class DeviceManagerImpl(connectivityHelper: ConnectivityHelper) : DeviceManager {

    private val liveData = ConnectivityLiveData(connectivityHelper)

    override fun getConnectivityState(): ConnectivityLiveData {
        return liveData
    }

}