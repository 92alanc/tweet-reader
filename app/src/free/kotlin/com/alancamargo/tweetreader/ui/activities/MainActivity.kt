package com.alancamargo.tweetreader.ui.activities

import android.os.Bundle
import com.alancamargo.tweetreader.ui.tools.AdLoader
import org.koin.android.ext.android.inject

class MainActivity : BaseMainActivity() {

    private val adLoader by inject<AdLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val target = findViewById<AdView>(R.id.ad_view_main)
        adLoader.loadNativeAds(target)*/
    }

}
