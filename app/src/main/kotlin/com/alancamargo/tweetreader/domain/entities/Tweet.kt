package com.alancamargo.tweetreader.domain.entities

data class Tweet(
    val id: Long,
    val creationDate: String,
    val fullText: String,
    val text: String,
    val author: User,
    val media: Media?,
    val quotedTweet: Tweet?,
    val retweet: Tweet?,
    val inReplyTo: Long?,
    val extendedTweet: ExtendedTweet?,
    val repliedTweet: Tweet?
)
