package com.alancamargo.tweetreader.data.tools

interface CrashReportManager {
    fun logException(t: Throwable)
}