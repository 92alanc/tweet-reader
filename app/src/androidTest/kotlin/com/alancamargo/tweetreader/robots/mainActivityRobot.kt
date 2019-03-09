package com.alancamargo.tweetreader.robots

import androidx.annotation.IdRes
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.activities.ProfileActivity

fun mainActivity(func: MainActivityRobot.() -> Unit) = MainActivityRobot().apply(func)

class MainActivityRobot {

    private var position = 0

    fun onTweetAtPosition(position: Int) {
        this.position = position
    }

    infix fun clickProfilePictureInTweet(func: MainActivityResult.() -> Unit) {
        clickRecyclerViewChild(R.id.img_profile_picture)
        applyResult(func)
    }

    infix fun clickAuthorNameInTweet(func: MainActivityResult.() -> Unit) {
        clickRecyclerViewChild(R.id.txt_name)
        applyResult(func)
    }

    infix fun clickAuthorScreenNameInTweet(func: MainActivityResult.() -> Unit) {
        clickRecyclerViewChild(R.id.txt_screen_name)
        applyResult(func)
    }

    private fun clickRecyclerViewChild(@IdRes viewId: Int) {
        recyclerView(R.id.recycler_view) {
            atPosition(position) {
                scroll()
                clickChildView(viewId)
            }
        }
    }

    private fun applyResult(func: MainActivityResult.() -> Unit) {
        MainActivityResult().run(func)
    }

}

class MainActivityResult {

    fun redirectToProfileActivity() {
        sentIntent {
            className(ProfileActivity::class.java.name)
        }
    }

}