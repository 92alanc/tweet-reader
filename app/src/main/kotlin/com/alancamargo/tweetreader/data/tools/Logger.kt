package com.alancamargo.tweetreader.data.tools

interface Logger {
    fun log(message: String)
    fun logException(t: Throwable)
}