package com.alancamargo.tweetreader.ui.ads

import android.text.method.LinkMovementMethod
import androidx.core.text.toSpannable
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.domain.tools.*
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.ads.extensions.getColour
import com.alancamargo.tweetreader.ui.ads.extensions.link
import com.google.android.material.textview.MaterialTextView

fun setTimestamp(textView: MaterialTextView, timestamp: String) {
    textView.text = formatDate(timestamp)
}

fun setMemberSince(textView: MaterialTextView, date: String) = textView.run {
    text = context.getString(R.string.member_since_format, formatDate(date))
}

fun setTweetText(
    textView: MaterialTextView,
    rawText: String,
    linkClickListener: LinkClickListener
) {
    val words = rawText.getWords()
    val formattedText = rawText.toSpannable()
    val textColour = textView.context.getColour(R.color.light_blue)

    for (word in words) {
        val isHashtag = word.isHashtag()
        val isMention = word.isMention()
        val isUrl = word.isUrl()

        if (word.isPlainText()) continue

        val linkType = when {
            isHashtag -> LinkClickListener.LinkType.HASHTAG
            isMention -> LinkClickListener.LinkType.MENTION
            isUrl -> LinkClickListener.LinkType.PLAIN_URL
            else -> LinkClickListener.LinkType.PLAIN_URL
        }

        formattedText.link(word, textColour, linkType, linkClickListener)
    }

    textView.run {
        movementMethod = LinkMovementMethod.getInstance()
        text = formattedText.trim()
    }
}