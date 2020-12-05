package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
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
) : Parcelable