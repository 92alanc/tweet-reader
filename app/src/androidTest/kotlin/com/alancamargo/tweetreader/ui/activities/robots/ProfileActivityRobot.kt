package com.alancamargo.tweetreader.ui.activities.robots

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.activities.BaseProfileActivity
import com.alancamargo.tweetreader.activities.ProfileActivity
import com.alancamargo.tweetreader.ui.activities.ProfileActivityTest
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.framework.entities.UserResponse
import com.alancamargo.tweetreader.tools.getJsonFromAsset
import com.alancamargo.tweetreader.tools.moshi
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityLiveData
import com.alancamargo.tweetreader.util.formatDate
import io.mockk.coVerify
import io.mockk.every
import java.text.NumberFormat

// region Launch modes
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
    profile = mockProfile()
    
    val intent = BaseProfileActivity.getIntent(context, profile)
    ActivityScenario.launch<ProfileActivity>(intent)

    return ProfileActivityRobot(context, profile, mockImageHandler).apply(block)
}

private fun mockProfile(): UserResponse {
    val json = getJsonFromAsset("user_details")
    val adapter = moshi.adapter(UserResponse::class.java)

    return adapter.fromJson(json)!!
}

private fun ProfileActivityTest.isConnected(isConnected: Boolean) {
    every { mockConnectivityHelper.isNetworkAvailable() } returns isConnected
}
// endregion

class ProfileActivityRobot(
    private val context: Context,
    private val profile: UserResponse,
    private val mockImageHandler: ImageHandler
) {

    infix fun assert(assertion: ProfileActivityAssertions.() -> Unit) {
        ProfileActivityAssertions(context, profile, mockImageHandler).run(assertion)
    }

}

class ProfileActivityAssertions(
    private val context: Context,
    private val profile: UserResponse,
    private val mockImageHandler: ImageHandler
) {

    fun disconnectedMessageIsDisplayed() {
        displayed {
            text(R.string.you_are_offline)
        }
    }

    fun profilePictureIsLoaded() {
        coVerify { mockImageHandler.loadImage(profile.profilePictureUrl, any()) }
    }

    fun profileBannerIsLoaded() {
        coVerify { mockImageHandler.loadImage(profile.profileBannerUrl, any()) }
    }

    fun nameIsDisplayed() {
        displayed {
            allOf {
                id(R.id.txt_name)
                text(profile.name)
            }
        }
    }

    fun screenNameIsDisplayed() {
        displayed {
            allOf {
                id(R.id.txt_screen_name)
                text("@${profile.screenName}")
            }
        }
    }

    fun descriptionIsDisplayed() {
        displayed {
            allOf {
                id(R.id.txt_description)
                text(profile.description)
            }
        }
    }

    fun locationIsDisplayed() {
        displayed {
            allOf {
                id(R.id.txt_location)
                text(profile.location)
            }
        }
    }

    fun memberSinceIsDisplayed() {
        val text = context.getString(R.string.member_since_format, formatDate(profile.creationDate))

        displayed {
            allOf {
                id(R.id.txt_member_since)
                text(text)
            }
        }
    }

    fun followersCountIsDisplayed() {
        val text = context.resources.getQuantityString(
            R.plurals.followers_count,
            profile.followersCount,
            NumberFormat.getNumberInstance().format(profile.followersCount)
        )

        displayed {
            allOf {
                id(R.id.txt_followers_count)
                text(text)
            }
        }
    }

}