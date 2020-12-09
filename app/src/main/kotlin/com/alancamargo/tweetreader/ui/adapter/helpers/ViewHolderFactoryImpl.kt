package com.alancamargo.tweetreader.ui.adapter.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.domain.entities.Media
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.adapter.viewholder.*
import com.alancamargo.tweetreader.ui.entities.UiMedia
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.ads.ImageHandler

class ViewHolderFactoryImpl(
    private val imageHandler: ImageHandler,
    private val linkClickListener: LinkClickListener,
    private val userMapper: EntityMapper<User, UiUser>,
    private val mediaMapper: EntityMapper<Media, UiMedia>
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
            shareButtonClickListener,
            userMapper
        )
    }

    override fun getVideoHolder(): VideoTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_video)

        return VideoTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper,
            mediaMapper
        )
    }

    override fun getLinkHolder(): LinkTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_link)

        return LinkTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper
        )
    }

    override fun getQuoteHolder(): QuotedTweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_quoted_tweet)

        return QuotedTweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper
        )
    }

    override fun getRetweetHolder(): RetweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_retweet)

        return RetweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper
        )
    }

    override fun getReplyHolder(): ReplyViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet_reply)

        return ReplyViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper
        )
    }

    override fun getTweetHolder(): TweetViewHolder {
        val itemView = inflater.inflate(R.layout.item_tweet)

        return TweetViewHolder(
            itemView,
            imageHandler,
            linkClickListener,
            shareButtonClickListener,
            userMapper
        )
    }

    private fun LayoutInflater.inflate(@LayoutRes layoutRes: Int): View {
        return inflate(layoutRes, parent, false)
    }

}