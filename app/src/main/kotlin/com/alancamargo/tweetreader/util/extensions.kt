package com.alancamargo.tweetreader.util

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.helpers.LinkClickListener

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

fun Spannable.link(
    word: String,
    textColour: Int,
    linkType: LinkClickListener.LinkType,
    linkClickListener: LinkClickListener
) {
    setSpan(
        object : ClickableTextSpan(textColour) {
            override fun onClick(widget: View) {
                linkClickListener.onLinkClicked(widget.context, word, linkType)
            }
        },
        toString().indexOf(word),
        toString().indexOf(word) + word.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}
