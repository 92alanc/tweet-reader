package com.alancamargo.tweetreader.activities

import android.os.Bundle
import com.alancamargo.tweetreader.util.extensions.loadBannerAds
import kotlinx.android.synthetic.free.activity_profile.*

class ProfileActivity : BaseProfileActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ad_view.loadBannerAds()
    }

}