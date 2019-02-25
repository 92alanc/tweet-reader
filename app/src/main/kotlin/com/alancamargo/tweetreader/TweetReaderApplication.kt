package com.alancamargo.tweetreader

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.connectivity.ConnectivityReceiver
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