package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

@Suppress("RegExpRedundantEscape")
fun String.getWords(): List<String> {
    return this.split(
        "(\\s|,|!|\\?|\\(|\\)\\[|]|\\{|\\}|<|>|;|\\+|-|\\*|$|(\\|)|\\\\)".toRegex()
    ).toMutableList().apply {
        removeAll { it == "" }
    }
}

fun AdView.loadAnnoyingAds() {
    val adRequest = AdRequest.Builder().build()
    loadAd(adRequest)
}