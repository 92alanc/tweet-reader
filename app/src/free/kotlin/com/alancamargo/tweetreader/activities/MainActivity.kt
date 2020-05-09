package com.alancamargo.tweetreader.activities

import android.os.Bundle
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.util.extensions.loadBannerAds
import com.google.android.gms.ads.AdView

class MainActivity : BaseMainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FIXME
        findViewById<AdView>(R.id.ad_view_main).loadBannerAds()
    }

}
