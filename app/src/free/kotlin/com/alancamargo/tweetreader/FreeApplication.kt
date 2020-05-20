package com.alancamargo.tweetreader

import com.alancamargo.tweetreader.di.FreeKoinModules
import com.alancamargo.tweetreader.di.KoinModules
import com.alancamargo.tweetreader.services.PaidVersionAdScheduler
import com.alancamargo.tweetreader.services.config.PaidVersionAdScheduleConfig
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.android.inject

@Suppress("unused")
class FreeApplication : TweetReaderApplication() {

    override val koinModules: KoinModules = FreeKoinModules()

    private val paidVersionAdConfig by inject<PaidVersionAdScheduleConfig>()

    override fun onCreate() {
        super.onCreate()

        if (paidVersionAdConfig.shouldScheduleBackgroundWork())
            scheduleBackgroundWork()
    }

    override fun startFirebase() {
        super.startFirebase()
        MobileAds.initialize(this, getString(R.string.admob_app_id))
    }

    private fun scheduleBackgroundWork() {
        val scheduler = PaidVersionAdScheduler(this)
        scheduler.scheduleBackgroundWork()
    }

}