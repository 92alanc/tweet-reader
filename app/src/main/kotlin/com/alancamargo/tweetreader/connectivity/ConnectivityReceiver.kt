package com.alancamargo.tweetreader.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.alancamargo.tweetreader.util.isConnected

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val isConnected = context.isConnected()
        ConnectivityMonitor.isConnected.postValue(isConnected)
        Log.d(javaClass.simpleName, "Connected: $isConnected")
    }

}