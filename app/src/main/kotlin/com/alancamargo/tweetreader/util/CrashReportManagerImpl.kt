package com.alancamargo.tweetreader.util

import com.crashlytics.android.Crashlytics

class CrashReportManagerImpl : CrashReportManager {

    override fun logException(t: Throwable) {
        Crashlytics.logException(t)
    }

}