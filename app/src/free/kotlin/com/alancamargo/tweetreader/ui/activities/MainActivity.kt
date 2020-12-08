package com.alancamargo.tweetreader.ui.activities

import android.os.Bundle
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.tools.AdLoader
import com.smaato.sdk.banner.widget.BannerView
import org.koin.android.ext.android.inject

class MainActivity : BaseMainActivity() {

    private val adLoader by inject<AdLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val target = findViewById<BannerView>(R.id.bannerView)
        adLoader.loadBannerAds(target, R.string.ads_banner_main)
    }

}
