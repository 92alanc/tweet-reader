package com.alancamargo.tweetreader.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.util.extensions.loadBannerAds
import com.alancamargo.tweetreader.util.setMemberSince
import com.alancamargo.tweetreader.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat

class ProfileActivity : AppCompatActivity(R.layout.activity_profile) {

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.title)
        intent.getParcelableExtra<User>(EXTRA_PROFILE).let(::bindData)
        ad_view.loadBannerAds()
    }

    private fun bindData(profile: User) {
        with(profile) {
            viewModel.loadPhoto(profileBannerUrl, img_profile_banner)
            viewModel.loadPhoto(profilePictureUrl, img_profile_picture)
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
            return Intent(context, ProfileActivity::class.java).putExtra(EXTRA_PROFILE, profile)
        }
    }

}