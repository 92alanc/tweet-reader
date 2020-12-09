package com.alancamargo.tweetreader

import com.smaato.sdk.core.Config
import com.smaato.sdk.core.SmaatoSdk
import com.smaato.sdk.core.log.LogLevel

@Suppress("unused")
class FreeApplication : App() {

    override fun onCreate() {
        super.onCreate()
        startSmaato()
    }

    private fun startSmaato() {
        val config = Config.builder()
            .setLogLevel(LogLevel.DEBUG)
            .build()

        val publisherId = getString(R.string.smaato_publisher_id)
        SmaatoSdk.init(this, config, publisherId)
    }

}