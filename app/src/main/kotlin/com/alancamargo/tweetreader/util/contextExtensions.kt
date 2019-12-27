package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.alancamargo.tweetreader.R

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.getColour(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.getVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Context.getAppName(): String = getString(R.string.app_name)
