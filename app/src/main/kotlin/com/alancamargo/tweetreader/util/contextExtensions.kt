package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
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

fun Context.showAppInfo(): Boolean {
    val title = "${getString(R.string.app_name)} ${getVersionName()}"
    val rawText = getString(R.string.developer_info)
    val textToHighlight = rawText.split("\n\n").last()
    val message = rawText.toSpannable()
        .bold(textToHighlight)
        .colour(textToHighlight, getColour(R.color.red))

    AlertDialog.Builder(this).setTitle(title)
        .setMessage(message)
        .setNeutralButton(R.string.ok, null)
        .show()

    return true
}

fun Context.showPrivacyTerms(): Boolean {
    val appName = getAppName()
    val message = getString(R.string.privacy_terms_format, appName, appName)

    AlertDialog.Builder(this)
        .setTitle(R.string.privacy_terms)
        .setMessage(message)
        .setNeutralButton(R.string.ok, null)
        .show()
    return true
}

private fun Context.getAppName(): String = getString(R.string.app_name)