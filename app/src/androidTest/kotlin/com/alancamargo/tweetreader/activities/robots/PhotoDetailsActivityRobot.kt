package com.alancamargo.tweetreader.activities.robots

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.PhotoDetailsActivity
import com.alancamargo.tweetreader.activities.PhotoDetailsActivityTest
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify

private const val MOCK_PHOTO_URL = "https://mock-photo-url"

fun PhotoDetailsActivityTest.launch(
    block: PhotoDetailsActivityRobot.() -> Unit
): PhotoDetailsActivityRobot {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val intent = PhotoDetailsActivity.getIntent(context, MOCK_PHOTO_URL)

    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    val scenario = ActivityScenario.launch<PhotoDetailsActivity>(intent)

    return PhotoDetailsActivityRobot(scenario, mockImageHandler).apply(block)
}

class PhotoDetailsActivityRobot(
    private val activityScenario: ActivityScenario<PhotoDetailsActivity>,
    private val mockImageHandler: ImageHandler
) {

    infix fun clickClose(assertion: PhotoDetailsActivityAssertions.() -> Unit) {
        click {
            id(R.id.bt_close)
        }

        assert(assertion)
    }

    infix fun assert(assertion: PhotoDetailsActivityAssertions.() -> Unit) {
        PhotoDetailsActivityAssertions(activityScenario, mockImageHandler).run(assertion)
    }

}

class PhotoDetailsActivityAssertions(
    private val activityScenario: ActivityScenario<PhotoDetailsActivity>,
    private val mockImageHandler: ImageHandler
) {

    fun photoIsLoaded() {
        coVerify { mockImageHandler.loadImage(MOCK_PHOTO_URL, any()) }
    }

    fun activityIsClosed() {
        assertThat(activityScenario.state).isEqualTo(Lifecycle.State.DESTROYED)
    }

}