package com.alancamargo.tweetreader.util

import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.databinding.BindingAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.di.OldDependencyInjection

@BindingAdapter("timestamp")
fun setTimestamp(textView: TextView, timestamp: String) {
    textView.text = formatDate(timestamp)
}

@BindingAdapter("memberSince")
fun setMemberSince(textView: TextView, date: String) = textView.run {
    text = context.getString(R.string.member_since_format, formatDate(date))
}

@BindingAdapter("tweetText")
fun setTweetText(textView: TextView, rawText: String) {
    val words = rawText.getWords()
    val formattedText = rawText.toSpannable()
    val textColour = textView.context.getColour(R.color.light_blue)

    for (word in words) {
        val isHashtag = word.isHashtag()
        val isMention = word.isMention()
        val isUrl = word.isUrl()

        if (word.isPlainText()) continue

        val linkType = when {
            isHashtag -> LinkType.HASHTAG
            isMention -> LinkType.MENTION
            isUrl -> LinkType.PLAIN_URL
            else -> LinkType.PLAIN_URL
        }

        formattedText.link(word, textColour, linkType)
    }

    textView.run {
        movementMethod = LinkMovementMethod.getInstance()
        text = formattedText.trim()
    }
}