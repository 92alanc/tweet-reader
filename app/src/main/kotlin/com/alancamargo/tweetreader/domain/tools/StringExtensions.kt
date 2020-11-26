package com.alancamargo.tweetreader.domain.tools

import com.alancamargo.tweetreader.domain.tools.REGEX_HASHTAG
import com.alancamargo.tweetreader.domain.tools.REGEX_MENTION
import com.alancamargo.tweetreader.domain.tools.REGEX_URL
import com.alancamargo.tweetreader.domain.tools.REGEX_WORDS

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
