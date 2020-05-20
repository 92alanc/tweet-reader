package com.alancamargo.tweetreader.services

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class PaidVersionAdScheduler(private val context: Context) {

    fun scheduleBackgroundWork() {
        val work = buildWork()
        scheduleWork(work)
    }

    private fun buildWork(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<PaidVersionAdWorker>(
            REPEAT_INTERVAL,
            TimeUnit.DAYS
        ).build()
    }

    private fun scheduleWork(work: PeriodicWorkRequest) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    private companion object {
        const val REPEAT_INTERVAL = 7L
        const val WORK_NAME = "PaidVersionAd"
    }

}