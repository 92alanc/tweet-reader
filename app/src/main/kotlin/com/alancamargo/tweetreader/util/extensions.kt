package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun String.getWords(): List<String> {
    return this.split(
        "(\\s|,|!|\\?|\\(|\\)\\[|]|\\{|\\}|<|>|;|\\+|-|\\*|$|(\\|)|\\\\)".toRegex()
    ).toMutableList().apply {
        removeAll { it == "" }
    }
}