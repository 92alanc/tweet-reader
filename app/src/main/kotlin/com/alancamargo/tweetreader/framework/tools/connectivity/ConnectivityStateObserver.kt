package com.alancamargo.tweetreader.framework.tools.connectivity

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.alancamargo.tweetreader.R
import com.google.android.material.snackbar.Snackbar

class ConnectivityStateObserver(
    private val deviceManager: DeviceManager
) : Observer<ConnectivityState> {

    private lateinit var connectedSnackbar: Snackbar
    private lateinit var disconnectedSnackbar: Snackbar

    fun observeConnectivityState(lifecycleOwner: LifecycleOwner, rootView: View) {
        connectedSnackbar = buildConnectedSnackbar(rootView)
        disconnectedSnackbar = buildDisconnectedSnackbar(rootView)
        deviceManager.getConnectivityState().observe(lifecycleOwner, this)
    }

    override fun onChanged(connectivityState: ConnectivityState) {
        if (connectivityState == ConnectivityState.CONNECTED) {
            if (disconnectedSnackbar.isShown) {
                disconnectedSnackbar.dismiss()
                connectedSnackbar.show()
            }
        } else {
            disconnectedSnackbar.show()
        }
    }

    private fun buildConnectedSnackbar(rootView: View): Snackbar {
        return Snackbar.make(rootView, R.string.you_are_back_online, Snackbar.LENGTH_SHORT)
    }

    private fun buildDisconnectedSnackbar(rootView: View): Snackbar {
        return Snackbar.make(rootView, R.string.you_are_offline, Snackbar.LENGTH_INDEFINITE)
    }

}