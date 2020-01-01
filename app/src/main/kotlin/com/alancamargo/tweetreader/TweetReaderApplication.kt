package com.alancamargo.tweetreader

import androidx.multidex.MultiDexApplication
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.util.AppImageHandler
import com.alancamargo.tweetreader.util.AppLinkClickListener
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

@Suppress("unused")
class TweetReaderApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        configureImageLoader()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        DependencyInjection.init(
            AppImageHandler(), BASE_URL,
            AppLinkClickListener()
        )
    }

    private fun configureImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}