package com.alancamargo.tweetreader.util

fun String.getWords(): List<String> {
    return this.split(REGEX_WORDS).toMutableList().apply {
        removeAll { it == "" }
    }
}

fun String.isHashtag() = REGEX_HASHTAG.matches(this)

fun String.isMention() = REGEX_MENTION.matches(this)

fun String.isUrl() = REGEX_URL.matches(this)

fun String.isPlainText() = !isHashtag() && !isMention() && !isUrl()

fun String.hasLink() = this.contains(REGEX_URL)
