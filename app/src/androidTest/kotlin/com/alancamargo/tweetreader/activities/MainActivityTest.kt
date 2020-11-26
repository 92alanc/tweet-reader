package com.alancamargo.tweetreader.activities

import android.content.Intent
import androidx.test.espresso.intent.Intents
import com.alancamargo.tweetreader.activities.robots.*
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityHelper
import com.alancamargo.tweetreader.framework.tools.connectivity.DeviceManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.After
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
        Intents.init()
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

    @Test
    fun whenClickingShareIconOnTweetItem_shouldShare() {
        coEvery {
            mockRepository.getShareIntent(any())
        } returns Result.Success(Intent(Intent.ACTION_VIEW))

        launchWithTweets {
        } clickShare {
            tweetIsShared()
        }
    }

    @Test
    fun whenClickingShareIconOnTweetItem_withError_shouldShowError() {
        coEvery { mockRepository.getShareIntent(any()) } returns Result.GenericError()

        launchWithTweets {
        } clickShare {
            showSharingError()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }

}