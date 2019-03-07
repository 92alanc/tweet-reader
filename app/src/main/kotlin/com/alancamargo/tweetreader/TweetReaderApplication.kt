package com.alancamargo.tweetreader

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import com.alancamargo.tweetreader.api.BASE_URL
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.connectivity.ConnectivityReceiver
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.util.AppImageHandler
import com.alancamargo.tweetreader.util.AppLinkClickListener
import com.alancamargo.tweetreader.util.isConnected
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

@Suppress("unused")
class TweetReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ConnectivityMonitor.isConnected = isConnected()
        watchConnectivity()
        configureImageLoader()
        DependencyInjection.init(AppImageHandler(), BASE_URL, AppLinkClickListener())
    }

    @Suppress("deprecation")
    private fun watchConnectivity() {
        if (SDK_INT >= LOLLIPOP) {
            ConnectivityMonitor().enable(this)
        } else {
            registerReceiver(
                ConnectivityReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    private fun configureImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}