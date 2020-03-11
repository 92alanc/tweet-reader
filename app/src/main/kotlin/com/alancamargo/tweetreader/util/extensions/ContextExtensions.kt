package com.alancamargo.tweetreader.util.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import com.alancamargo.tweetreader.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.getColour(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.getVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}

fun Context.showAppInfo() {
    val title = "${getString(R.string.app_name)} ${getVersionName()}"
    val rawText = getString(R.string.developer_info)
    val textToHighlight = rawText.split("\n\n").last()
    val message = rawText.toSpannable()
        .bold(textToHighlight)
        .colour(textToHighlight, getColour(R.color.red))

    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(R.string.ok, null)
        .show()
}

fun Context.showPrivacyTerms() {
    val appName = getAppName()
    val message = getString(R.string.privacy_terms_format, appName, appName)

    MaterialAlertDialogBuilder(this)
        .setTitle(R.string.privacy_terms)
        .setMessage(message)
        .setNeutralButton(R.string.ok, null)
        .show()
}

private fun Context.getAppName(): String = getString(R.string.app_name)