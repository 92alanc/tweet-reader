package com.alancamargo.tweetreader.util.extensions

import com.alancamargo.tweetreader.util.REGEX_HASHTAG
import com.alancamargo.tweetreader.util.REGEX_MENTION
import com.alancamargo.tweetreader.util.REGEX_URL
import com.alancamargo.tweetreader.util.REGEX_WORDS

fun String.getWords(): List<String> {
    return this.split(REGEX_WORDS).toMutableList().apply {
        removeAll { it == "" }
    }
}

fun String.extractLink() = getWords().find { it.isUrl() }

fun String.isHashtag() = REGEX_HASHTAG.matches(this)

fun String.isMention() = REGEX_MENTION.matches(this)

fun String.isUrl() = REGEX_URL.matches(this)

fun String.isPlainText() = !isHashtag() && !isMention() && !isUrl()

fun String.hasLink() = this.contains(REGEX_URL)
