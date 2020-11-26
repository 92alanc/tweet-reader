package com.alancamargo.tweetreader.ui.activities.robots

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.MainActivity
import com.alancamargo.tweetreader.ui.activities.MainActivityTest
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.framework.entities.Tweet
import com.alancamargo.tweetreader.tools.getJsonFromAsset
import com.alancamargo.tweetreader.tools.moshi
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityLiveData
import com.squareup.moshi.Types
import io.mockk.coEvery
import io.mockk.every

// region Launch modes
fun MainActivityTest.launchWithTweets(block: MainActivityRobot.() -> Unit): MainActivityRobot {
    isConnected(true)
    mockTweets()
    return launch(block)
}

fun MainActivityTest.launchWithoutTweets(block: MainActivityRobot.() -> Unit): MainActivityRobot {
    isConnected(true)
    mockNoResults()
    return launch(block)
}

fun MainActivityTest.launchWithSuspendedAccount(
    block: MainActivityRobot.() -> Unit
): MainActivityRobot {
    isConnected(true)
    mockSuspendedAccount()
    return launch(block)
}

fun MainActivityTest.launchWithGenericError(
    block: MainActivityRobot.() -> Unit
): MainActivityRobot {
    isConnected(true)
    mockGenericError()
    return launch(block)
}

fun MainActivityTest.launchDisconnected(block: MainActivityRobot.() -> Unit): MainActivityRobot {
    isConnected(false)
    mockNetworkError()
    return launch(block)
}

private fun MainActivityTest.launch(block: MainActivityRobot.() -> Unit): MainActivityRobot {
    every {
        mockDeviceManager.getConnectivityState()
    } returns ConnectivityLiveData(mockConnectivityHelper)

    ActivityScenario.launch(MainActivity::class.java)

    return MainActivityRobot().apply(block)
}

private fun MainActivityTest.isConnected(isConnected: Boolean) {
    every { mockConnectivityHelper.isNetworkAvailable() } returns isConnected
}

private fun MainActivityTest.mockTweets() {
    val json = getJsonFromAsset("tweets")
    val type = Types.newParameterizedType(List::class.java, Tweet::class.java)
    val adapter = moshi.adapter<List<Tweet>>(type)
    val tweets = adapter.fromJson(json)!!
    val result = Result.Success(tweets)

    coEvery { mockRepository.getTweets(any(), any()) } returns result
}

private fun MainActivityTest.mockNoResults() {
    coEvery { mockRepository.getTweets(any(), any()) } returns Result.Success(emptyList())
}

private fun MainActivityTest.mockNetworkError() {
    coEvery { mockRepository.getTweets(any(), any()) } returns Result.NetworkError
}

private fun MainActivityTest.mockSuspendedAccount() {
    coEvery { mockRepository.getTweets(any(), any()) } returns Result.AccountSuspendedError
}

private fun MainActivityTest.mockGenericError() {
    coEvery { mockRepository.getTweets(any(), any()) } returns Result.GenericError()
}
// endregion

class MainActivityRobot {

    infix fun clickShare(assertion: MainActivityAssertions.() -> Unit) {
        recyclerView(R.id.recycler_view) {
            atPosition(0) {
                clickChildView(R.id.bt_share)
            }
        }

        assert(assertion)
    }

    infix fun assert(assertion: MainActivityAssertions.() -> Unit) {
        MainActivityAssertions().run(assertion)
    }

}

class MainActivityAssertions {

    fun showTweets() {
        recyclerView(R.id.recycler_view) {
            sizeIs(6)
        }
    }

    fun showDisconnectedError() {
        showError(R.string.message_disconnected, R.drawable.ic_disconnected)
    }

    fun showAccountSuspendedError() {
        showError(R.string.message_account_suspended, R.drawable.ic_account_suspended)
    }

    fun showGenericError() {
        showError(R.string.message_generic_error, R.drawable.ic_error)
    }

    fun showNoResultsError() {
        showError(R.string.message_no_results, R.drawable.ic_no_results)
    }

    fun tweetIsShared() {
        sentIntent {
            action(Intent.ACTION_VIEW)
        }
    }

    fun showSharingError() {
        displayed {
            text(R.string.error_sharing_tweet)
        }
    }

    private fun showError(@StringRes text: Int, @DrawableRes image: Int) {
        displayed {
            allOf {
                id(R.id.txt_error)
                text(text)
            }
        }

        displayed {
            allOf {
                id(R.id.img_error)
                image(image)
            }
        }
    }

}