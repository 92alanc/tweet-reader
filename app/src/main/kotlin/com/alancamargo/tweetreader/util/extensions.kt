package com.alancamargo.tweetreader.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.connectivity.ConnectivityMonitor
import com.alancamargo.tweetreader.di.DependencyInjection
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun AdView.loadAnnoyingAds() {
    val adRequest = AdRequest.Builder().build()
    loadAd(adRequest)
}

fun AppCompatActivity.watchConnectivityState(snackbarView: View) {
    val snackbar = Snackbar.make(snackbarView, R.string.you_are_offline, Snackbar.LENGTH_INDEFINITE)
    ConnectivityMonitor.isConnected.observe(this, Observer { isConnected ->
        if (!isConnected)
            snackbar.show()
        else
            snackbar.dismiss()
    })
}

fun CoroutineScope.runAsync(func: () -> Unit) {
    launch {
        withContext(Dispatchers.Default) {
            func()
        }
    }
}

fun <V: View> RecyclerView.ViewHolder.bindView(@IdRes idRes: Int) = lazy {
    itemView.findViewById<V>(idRes)
}

fun Spannable.bold(text: String) = apply {
    setSpan(
        StyleSpan(Typeface.BOLD),
        toString().indexOf(text),
        toString().indexOf(text) + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun Spannable.colour(text: String, colour: Int) = apply {
    setSpan(
        ForegroundColorSpan(colour),
        toString().indexOf(text),
        toString().indexOf(text) + text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

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
