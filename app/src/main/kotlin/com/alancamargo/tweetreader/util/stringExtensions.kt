package com.alancamargo.tweetreader.util

@Suppress("RegExpRedundantEscape")
fun String.getWords(): List<String> {
    return this.split(
        "(\\s|,|\\.|!|\\?|\\(|\\)\\[|]|\\{|\\}|<|>|;|\\+|-|\\*|$|(\\|)|\\\\)".toRegex()
    ).toMutableList().apply {
        removeAll { it == "" }
    }
}

fun String.isHashtag() = REGEX_HASHTAG.matches(this)

fun String.isMention() = REGEX_MENTION.matches(this)

fun String.isUrl() = REGEX_URL.matches(this)

fun String.isPlainText() = !isHashtag() && !isMention() && !isUrl()

fun String.hasLink() = this.contains(REGEX_URL)
