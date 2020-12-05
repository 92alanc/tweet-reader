package com.alancamargo.tweetreader.domain.entities

import com.alancamargo.tweetreader.data.remote.MEDIA_PHOTO
import com.alancamargo.tweetreader.data.remote.MEDIA_VIDEO

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
) {

    fun isQuoting() = quotedTweet != null

    fun isRetweet() = retweet != null

    fun isReply() = inReplyTo != null

    fun containsPhoto() = media?.contents?.any { it.type == MEDIA_PHOTO } ?: false

    fun containsVideo() = media?.contents?.any { it.type == MEDIA_VIDEO } ?: false

}
