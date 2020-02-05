package com.alancamargo.tweetreader.util.device

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class DeviceManagerTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @MockK lateinit var mockConnectivityHelper: ConnectivityHelper
    @MockK lateinit var mockObserver: Observer<ConnectivityState>

    private lateinit var deviceManager: DeviceManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        deviceManager = DeviceManagerImpl(mockConnectivityHelper)
    }

    @Test
    fun whenOnline_connectivityStateShouldBeConnected() {
        every { mockConnectivityHelper.isNetworkAvailable() } returns true
        deviceManager.getConnectivityState().observeForever(mockObserver)

        verify { mockObserver.onChanged(ConnectivityState.CONNECTED) }
    }

    @Test
    fun whenOffline_connectivityStateShouldBeDisconnected() {
        every { mockConnectivityHelper.isNetworkAvailable() } returns false
        deviceManager.getConnectivityState().observeForever(mockObserver)

        verify { mockObserver.onChanged(ConnectivityState.DISCONNECTED) }
    }

}