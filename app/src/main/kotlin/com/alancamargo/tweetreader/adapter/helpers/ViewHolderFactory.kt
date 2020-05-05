package com.alancamargo.tweetreader.adapter.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alancamargo.tweetreader.adapter.viewholder.*
import com.alancamargo.tweetreader.listeners.ShareButtonClickListener

interface ViewHolderFactory {

    fun init(
        inflater: LayoutInflater,
        parent: ViewGroup,
        shareButtonClickListener: ShareButtonClickListener?
    )

    fun getPhotoHolder(): PhotoTweetViewHolder
    fun getVideoHolder(): VideoTweetViewHolder
    fun getLinkHolder(): LinkTweetViewHolder
    fun getQuoteHolder(): QuotedTweetViewHolder
    fun getRetweetHolder(): RetweetViewHolder
    fun getReplyHolder(): ReplyViewHolder
    fun getTweetHolder(): TweetViewHolder

}