package com.alancamargo.tweetreader.activities.robots

import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.activities.ProfileActivityTest
import com.alancamargo.tweetreader.util.device.ConnectivityLiveData
import io.mockk.every

fun ProfileActivityTest.launchConnected(
    block: ProfileActivityRobot.() -> Unit
): ProfileActivityRobot {
    isConnected(true)
    return launch(block)
}

fun ProfileActivityTest.launchDisconnected(
    block: ProfileActivityRobot.() -> Unit
): ProfileActivityRobot {
    isConnected(false)
    return launch(block)
}

private fun ProfileActivityTest.launch(
    block: ProfileActivityRobot.() -> Unit
): ProfileActivityRobot {
    every {
        mockDeviceManager.getConnectivityState()
    } returns ConnectivityLiveData(mockConnectivityHelper)

    val context = InstrumentationRegistry.getInstrumentation().targetContext
    
    val intent = ProfileActivity.getIntent(context)
    ActivityScenario.launch<ProfileActivity>(intent)

    return ProfileActivityRobot().apply(block)
}

private fun ProfileActivityTest.isConnected(isConnected: Boolean) {
    every { mockConnectivityHelper.isNetworkAvailable() } returns isConnected
}

class ProfileActivityRobot {



}

class ProfileActivityAssertions {



}