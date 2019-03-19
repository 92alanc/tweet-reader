package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.api.CODE_FORBIDDEN
import com.alancamargo.tweetreader.base.BaseActivityTest
import com.alancamargo.tweetreader.robots.mainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Test

class MainActivityTest : BaseActivityTest<MainActivity>(MainActivity::class) {

    @Test
    fun whenClickOnAuthorName_shouldRedirectToProfileActivity() {
        mockTokenResponse()
        mockTweetsResponse()

        mainActivity {
            onTweetAtPosition(0)
        } clickAuthorNameInTweet {
            redirectToProfileActivity()
        }
    }

    @Test
    fun whenClickOnAuthorScreenName_shouldRedirectToProfileActivity() {
        mockTokenResponse()
        mockTweetsResponse()

        mainActivity {
            onTweetAtPosition(0)
        } clickAuthorScreenNameInTweet {
            redirectToProfileActivity()
        }
    }

    @Test
    fun whenClickOnAuthorProfilePicture_shouldRedirectToProfileActivity() {
        mockTokenResponse()
        mockTweetsResponse()

        mainActivity {
            onTweetAtPosition(0)
        } clickProfilePictureInTweet {
            redirectToProfileActivity()
        }
    }

    @Test
    fun whenAccountIsSuspended_shouldDisplayMessage() {
        mockTokenResponse()
        mockAccountSuspendedResponse()

        mainActivity {
        } assert {
            accountSuspendedMessageIsDisplayed()
        }
    }

    @Test
    fun whenAccountIsSuspended_shouldNotDisplayTweetList() {
        mockTokenResponse()
        mockAccountSuspendedResponse()

        mainActivity {
        } assert {
            tweetListIsNotDisplayed()
        }
    }

    private fun mockTokenResponse() {
        val body = getJsonFromAsset("token.json")
        enqueueResponseBody(body, 200)
    }

    private fun mockTweetsResponse() {
        val body = getJsonFromAsset("tweets.json")
        enqueueResponseBody(body, 200)
    }

    private fun mockProfileDetailsResponse() {
        val body = getJsonFromAsset("profile_details.json")
        enqueueResponseBody(body, 200)
    }

    private fun mockAccountSuspendedResponse() {
        val body = getJsonFromAsset("account_suspended.json")
        enqueueResponseBody(body, CODE_FORBIDDEN)
    }

    private fun enqueueResponseBody(body: String, code: Int) {
        api.enqueue(
            MockResponse().setBody(body)
                .setResponseCode(code)
                .setSocketPolicy(SocketPolicy.KEEP_OPEN)
        )
    }

}