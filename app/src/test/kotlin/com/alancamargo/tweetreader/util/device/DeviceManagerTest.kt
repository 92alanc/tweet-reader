package com.alancamargo.tweetreader.util.device

import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class DeviceManagerTest {

    @MockK lateinit var mockConnectivityHelper: ConnectivityHelper
    @MockK lateinit var mockObserver: Observer<ConnectivityState>

    private lateinit var deviceManager: DeviceManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deviceManager = DeviceManagerImpl(mockConnectivityHelper)
    }

    @Test
    @Ignore("NullPointerException")
    fun whenOnline_connectivityStateShouldBeConnected() {
        every { mockConnectivityHelper.isNetworkAvailable() } returns true
        deviceManager.getConnectivityState().observeForever(mockObserver)

        verify { mockObserver.onChanged(ConnectivityState.CONNECTED) }
    }

    @Test
    @Ignore("NullPointerException")
    fun whenOffline_connectivityStateShouldBeDisconnected() {
        every { mockConnectivityHelper.isNetworkAvailable() } returns false
        deviceManager.getConnectivityState().observeForever(mockObserver)

        verify { mockObserver.onChanged(ConnectivityState.DISCONNECTED) }
    }

}