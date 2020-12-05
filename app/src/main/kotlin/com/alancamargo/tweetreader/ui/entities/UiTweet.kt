package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import com.alancamargo.tweetreader.data.remote.MEDIA_PHOTO
import com.alancamargo.tweetreader.data.remote.MEDIA_VIDEO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiTweet(
        val id: Long,
        val creationDate: String,
        val fullText: String,
        val text: String,
        val author: UiUser,
        val media: UiMedia?,
        val quotedTweet: UiTweet?,
        val retweet: UiTweet?,
        val inReplyTo: Long?,
        val extendedTweet: UiExtendedTweet?,
        val repliedTweet: UiTweet?
) : Parcelable {

    fun isQuoting() = quotedTweet != null

    fun isRetweet() = retweet != null

    fun isReply() = inReplyTo != null

    fun containsPhoto() = media?.contents?.any { it.type == MEDIA_PHOTO } ?: false

    fun containsVideo() = media?.contents?.any { it.type == MEDIA_VIDEO } ?: false

}