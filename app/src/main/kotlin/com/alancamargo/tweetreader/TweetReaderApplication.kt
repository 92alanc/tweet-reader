package com.alancamargo.tweetreader

import androidx.multidex.MultiDexApplication
import com.alancamargo.tweetreader.di.getModules
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class TweetReaderApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startFirebase()
        startDependencyInjection()
        startImageLoader()
    }

    private fun startFirebase() {
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
    }

    private fun startDependencyInjection() {
        startKoin {
            androidContext(this@TweetReaderApplication)
            modules(getModules())
        }
    }

    private fun startImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}