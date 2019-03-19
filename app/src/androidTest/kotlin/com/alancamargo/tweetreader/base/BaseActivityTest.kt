package com.alancamargo.tweetreader.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.utils.doWait
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.mock.MockImageHandler
import com.alancamargo.tweetreader.mock.MockLinkClickListener
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.InputStream
import kotlin.reflect.KClass

@Suppress("BooleanLiteralArgument")
open class BaseActivityTest<T: AppCompatActivity>(
    activityClass: KClass<T>,
    private val autoLaunch: Boolean = true
) {

    val api = MockWebServer()

    @Rule
    @JvmField
    val rule = if (autoLaunch) {
        IntentsTestRule(activityClass.java, true, false)
    } else {
        ActivityTestRule(activityClass.java, true, false)
    }

    @Before
    open fun setup() {
        DependencyInjection.init(
            MockImageHandler,
            api.url("/mock-twitter/").toString(),
            MockLinkClickListener
        )

        if (autoLaunch)
            launch(intent())
        else
            Intents.init()
    }

    @After
    open fun tearDown() {
        if (!autoLaunch)
            Intents.release()
    }

    open fun intent() = Intent()

    fun getJsonFromAsset(fileName: String): String {
        val json: String

        try {
            val inputStream: InputStream = rule.activity.assets.open("fixtures/$fileName")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }

        return json.replace("\n", "")
    }

    private fun launch(intent: Intent) {
        rule.launchActivity(intent)
        doWait(300)
    }

}