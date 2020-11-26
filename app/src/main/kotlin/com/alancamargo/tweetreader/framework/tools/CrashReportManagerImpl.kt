package com.alancamargo.tweetreader.framework.tools

import android.util.Log
import com.alancamargo.tweetreader.data.tools.CrashReportManager
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Log.e("ERROR_TWEET_READER", t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

}