package com.alancamargo.tweetreader.adapter.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.viewholder.*
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.listeners.LinkClickListener
import com.alancamargo.tweetreader.listeners.ShareButtonClickListener

class ViewHolderFactoryImpl(
    private val imageHandler: ImageHandler,
    private val linkClickListener: LinkClickListener
) : ViewHolderFactory {

    private lateinit var inflater: LayoutInflater
    private lateinit var parent: ViewGroup
    private var shareButtonClickListener: ShareButtonClickListener? = null

    override fun init(
        inflater: LayoutInflater,
        parent: ViewGroup,
        shareButtonClickListener: ShareButtonClickListener?
    ) {
        this.inflater = inflater
        this.parent = parent
        this.shareButtonClickListener = shareButtonClickListener
    }

    override fun getPhotoHolder(): PhotoTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_photo)

        return PhotoTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getVideoHolder(): VideoTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_video)

        return VideoTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getLinkHolder(): LinkTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_link)

        return LinkTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getQuoteHolder(): QuotedTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_quoted_tweet)

        return QuotedTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getRetweetHolder(): RetweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_retweet)

        return RetweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getReplyHolder(): ReplyViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_reply)

        return ReplyViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    override fun getTweetHolder(): TweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet)

        return TweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener
        )
    }

    private fun LayoutInflater.inflate(@LayoutRes layoutRes: Int): View {
        return inflate(layoutRes, parent, false)
    }

}