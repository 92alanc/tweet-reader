package com.alancamargo.tweetreader.util

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Log.e("ERROR_TWEET_READER", t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

}