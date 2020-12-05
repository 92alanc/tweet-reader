package com.alancamargo.tweetreader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.framework.tools.connectivity.ConnectivityStateObserver
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.tools.setMemberSince
import kotlinx.android.synthetic.main.activity_profile_base.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.NumberFormat

open class BaseProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val connectivityStateObserver by inject<ConnectivityStateObserver>()
    private val imageHandler by inject<ImageHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        intent.getParcelableExtra<UiUser>(EXTRA_PROFILE)?.let(::bindData)
        connectivityStateObserver.observeConnectivityState(this, profile_activity_root)
    }

    private fun bindData(profile: UiUser) {
        with(profile) {
            lifecycleScope.launch {
                with(imageHandler) {
                    loadImage(profileBannerUrl, img_profile_banner)
                    loadImage(profilePictureUrl, img_profile_picture)
                }
                txt_name.text = name
                txt_screen_name.text = getString(R.string.screen_name_format, screenName)
                txt_description.text = description
                txt_location.text = location
                setMemberSince(txt_member_since, creationDate)
                txt_followers_count.text = resources.getQuantityString(
                    R.plurals.followers_count,
                    followersCount,
                    NumberFormat.getNumberInstance().format(followersCount)
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PROFILE = "com.alancamargo.tweetreader.EXTRA_PROFILE"

        fun getIntent(context: Context, profile: UiUser): Intent {
            return Intent(context, ProfileActivity::class.java).putExtra(EXTRA_PROFILE, profile)
        }
    }

}