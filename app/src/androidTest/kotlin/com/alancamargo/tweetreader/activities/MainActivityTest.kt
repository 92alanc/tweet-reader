package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.base.BaseActivityTest
import com.alancamargo.tweetreader.robots.mainActivity
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

class MainActivityTest : BaseActivityTest<MainActivity>(MainActivity::class) {

    @Test
    fun whenClickOnAuthorName_shouldRedirectToProfileActivity() {
        mainActivity {
            onTweetAtPosition(0)
        } clickAuthorNameInTweet {
            redirectToProfileActivity()
        }
    }

    @Test
    fun whenClickOnAuthorScreenName_shouldRedirectToProfileActivity() {
        mainActivity {
            onTweetAtPosition(0)
        } clickAuthorScreenNameInTweet {
            redirectToProfileActivity()
        }
    }

    @Test
    fun whenClickOnAuthorProfilePicture_shouldRedirectToProfileActivity() {
        mainActivity {
            onTweetAtPosition(0)
        } clickProfilePictureInTweet {
            redirectToProfileActivity()
        }
    }

    private fun mockTweetsResponse() {
        val body = getJsonFromAsset("tweets.json")
        api.enqueue(MockResponse().setBody(body).setResponseCode(200))
    }

    private fun mockProfileDetailsResponse() {
        val body = getJsonFromAsset("profile_details.json")
        api.enqueue(MockResponse().setBody(body).setResponseCode(200))
    }

    private fun mockAccountSuspendedResponse() {
        val body = getJsonFromAsset("account_suspended.json")
        api.enqueue(MockResponse().setBody(body).setResponseCode(403))
    }

}