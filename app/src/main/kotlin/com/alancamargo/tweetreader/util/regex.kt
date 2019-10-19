package com.alancamargo.tweetreader.util

val REGEX_HASHTAG = "#(.)+".toRegex()
val REGEX_MENTION = "@([a-z]|[A-Z]|[0-9]|_|\\.|-)+".toRegex()
val REGEX_URL = "((http|https)(://))*([a-z]|[A-Z]|[0-9]|[.]|-|/|&|\\?|#|_|=)+".toRegex()