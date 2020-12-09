package com.alancamargo.tweetreader.framework.tools

import android.util.Log
import com.alancamargo.tweetreader.data.tools.Logger
import com.google.firebase.crashlytics.FirebaseCrashlytics

class LoggerImpl : Logger {

    override fun log(message: String) {
        Log.d("DEBUG_TWEET_READER", message)
        FirebaseCrashlytics.getInstance().log(message)
    }

    override fun logException(t: Throwable) {
        Log.e("ERROR_TWEET_READER", t.message, t)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

}