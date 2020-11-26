package com.alancamargo.tweetreader.ui.activities

import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.framework.entities.User
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityHelper
import com.alancamargo.tweetreader.framework.tools.connectivity.DeviceManager
import com.alancamargo.tweetreader.ui.activities.robots.launchConnected
import com.alancamargo.tweetreader.ui.activities.robots.launchDisconnected
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ProfileActivityTest : KoinTest {

    @MockK lateinit var mockConnectivityHelper: ConnectivityHelper

    val mockDeviceManager by inject<DeviceManager>()
    val mockImageHandler by inject<ImageHandler>()

    lateinit var profile: User

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        loadKoinModules(getTestModules())
    }

    @Test
    fun shouldLoadProfilePicture() {
        launchConnected {
        } assert {
            profilePictureIsLoaded()
        }
    }

    @Test
    fun shouldLoadProfileBanner() {
        launchConnected {
        } assert {
            profileBannerIsLoaded()
        }
    }

    @Test
    fun shouldDisplayName() {
        launchConnected {
        } assert {
            nameIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayScreenName() {
        launchConnected {
        } assert {
            screenNameIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayDescription() {
        launchConnected {
        } assert {
            descriptionIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayLocation() {
        launchConnected {
        } assert {
            locationIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayMemberSince() {
        launchConnected {
        } assert {
            memberSinceIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayFollowersCount() {
        launchConnected {
        } assert {
            followersCountIsDisplayed()
        }
    }

    @Test
    fun whenOffline_shouldShowMessage() {
        launchDisconnected {
        } assert {
            disconnectedMessageIsDisplayed()
        }
    }

}