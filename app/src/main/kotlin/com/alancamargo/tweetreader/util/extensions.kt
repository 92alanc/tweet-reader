package com.alancamargo.tweetreader.util

import android.content.Context
import android.net.ConnectivityManager
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.alancamargo.tweetreader.di.DependencyInjection
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

fun String.toSpannable() = SpannableString(this)

fun Spannable.link(word: String, textColour: Int, linkType: LinkType) {
    setSpan(
        object : ClickableTextSpan(textColour) {
            override fun onClick(widget: View) {
                DependencyInjection.linkClickListener.onLinkClicked(
                    widget.context,
                    word,
                    linkType
                )
            }
        },
        toString().indexOf(word),
        toString().indexOf(word) + word.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun Context.getColour(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun String.isHashtag() = REGEX_HASHTAG.matches(this)

fun String.isMention() = REGEX_MENTION.matches(this)

fun String.isUrl() = REGEX_URL.matches(this)

fun String.isPlainText() = !isHashtag() && !isMention() && !isUrl()