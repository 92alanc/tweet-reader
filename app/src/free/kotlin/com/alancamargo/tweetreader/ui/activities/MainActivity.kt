package com.alancamargo.tweetreader.ui.activities

import android.os.Bundle
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.ads.AdLoader
import com.smaato.sdk.banner.widget.BannerView
import org.koin.android.ext.android.inject

class MainActivity : BaseMainActivity() {

    private val adLoader by inject<AdLoader>()

    private val banner by lazy { findViewById<BannerView>(R.id.bannerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adLoader.loadBannerAds(banner, R.string.ads_banner_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        adLoader.destroyBannerAds(banner)
    }

}
