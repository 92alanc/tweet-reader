package com.alancamargo.tweetreader.util

fun extractLinkFrom(str: String) = str.getWords().find { it.isUrl() }