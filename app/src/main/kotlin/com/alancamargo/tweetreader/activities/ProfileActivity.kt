package com.alancamargo.tweetreader.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.loadAnnoyingAds
import com.alancamargo.tweetreader.util.setImageUrl
import com.alancamargo.tweetreader.util.setMemberSince
import com.alancamargo.tweetreader.util.watchConnectivityState
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.NumberFormat

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = getString(R.string.title)
        intent.getParcelableExtra<User>(EXTRA_PROFILE).let(::bindData)
        ad_view.loadAnnoyingAds()
        watchConnectivityState(ad_view)
    }

    private fun bindData(profile: User) {
        with(profile) {
            setImageUrl(img_profile_banner, profileBannerUrl)
            setImageUrl(img_profile_picture, profilePictureUrl)
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

    companion object {
        private const val EXTRA_PROFILE = "com.alancamargo.tweetreader.EXTRA_PROFILE"

        fun getIntent(context: Context, profile: User): Intent {
            return Intent(context, ProfileActivity::class.java)
                .putExtra(EXTRA_PROFILE, profile)
        }
    }

}