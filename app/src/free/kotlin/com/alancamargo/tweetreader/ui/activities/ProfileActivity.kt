package com.alancamargo.tweetreader.ui.activities

import android.os.Bundle
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.activities.BaseProfileActivity
import com.alancamargo.tweetreader.ui.tools.extensions.loadBannerAds
import com.google.android.gms.ads.AdView

class ProfileActivity : BaseProfileActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<AdView>(R.id.ad_view_profile).loadBannerAds()
    }

}