package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.activities.robots.*
import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.device.ConnectivityHelper
import com.alancamargo.tweetreader.util.device.DeviceManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class MainActivityTest : KoinTest {

    @MockK lateinit var mockConnectivityHelper: ConnectivityHelper

    val mockRepository by inject<TweetRepository>()
    val mockDeviceManager by inject<DeviceManager>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        loadKoinModules(getTestModules())
    }

    @Test
    fun shouldDisplayTweets() {
        launchWithTweets {
        } assert {
            showTweets()
        }
    }

    @Test
    fun whenNoResultsAreReceived_shouldShowCorrectError() {
        launchWithoutTweets {
        } assert {
            showNoResultsError()
        }
    }

    @Test
    fun whenOffline_shouldShowCorrectError() {
        launchDisconnected {
        } assert {
            showDisconnectedError()
        }
    }

    @Test
    fun whenAccountIsSuspended_shouldShowCorrectError() {
        launchWithSuspendedAccount {
        } assert {
            showAccountSuspendedError()
        }
    }

    @Test
    fun withGenericError_shouldShowCorrectError() {
        launchWithGenericError {
        } assert {
            showGenericError()
        }
    }

}