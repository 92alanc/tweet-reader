package com.alancamargo.tweetreader.util

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.text.toSpannable
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.util.extensions.*

fun setTimestamp(textView: TextView, timestamp: String) {
    textView.text = formatDate(timestamp)
}

fun setMemberSince(textView: TextView, date: String) = textView.run {
    text = context.getString(R.string.member_since_format, formatDate(date))
}

fun setTweetText(textView: TextView, rawText: String, linkClickListener: LinkClickListener) {
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