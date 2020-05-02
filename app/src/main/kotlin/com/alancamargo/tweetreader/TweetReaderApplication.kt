package com.alancamargo.tweetreader

import android.app.Application
import com.alancamargo.tweetreader.di.getModules
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("registered")
open class TweetReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startFirebase()
        startDependencyInjection()
        startImageLoader()
    }

    open fun startFirebase() {
        FirebaseApp.initializeApp(this)
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