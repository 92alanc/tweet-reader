package com.alancamargo.tweetreader

import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.TweetReaderApplication
import com.google.android.gms.ads.MobileAds

@Suppress("unused")
class FreeApplication : TweetReaderApplication() {

    override fun startFirebase() {
        super.startFirebase()
        MobileAds.initialize(this, getString(R.string.admob_app_id))
    }

}