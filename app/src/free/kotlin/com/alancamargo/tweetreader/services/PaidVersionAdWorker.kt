package com.alancamargo.tweetreader.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PaidVersionAdWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        if (!isPaidVersionInstalled()) {
            TODO("do stuff")
        }

        return Result.success()
    }

    private fun isPaidVersionInstalled(): Boolean {
        val packageName = "${context.packageName}.paid"
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        return intent != null
    }

}