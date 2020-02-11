package com.alancamargo.tweetreader.util

interface CrashReportManager {
    fun logException(t: Throwable)
}