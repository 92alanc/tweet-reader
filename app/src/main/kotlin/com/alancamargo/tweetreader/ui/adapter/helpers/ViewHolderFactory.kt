package com.alancamargo.tweetreader.ui.adapter.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alancamargo.tweetreader.ui.adapter.viewholder.*
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener

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