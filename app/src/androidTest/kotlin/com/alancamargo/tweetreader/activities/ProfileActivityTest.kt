package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.util.device.ConnectivityHelper
import com.alancamargo.tweetreader.util.device.DeviceManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ProfileActivityTest : KoinTest {

    @MockK lateinit var mockConnectivityHelper: ConnectivityHelper

    val mockDeviceManager by inject<DeviceManager>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        loadKoinModules(getTestModules())
    }

}