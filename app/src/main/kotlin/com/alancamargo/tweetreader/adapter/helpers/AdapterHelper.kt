package com.alancamargo.tweetreader.adapter.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.adapter.viewholder.QuotedTweetViewHolder
import com.alancamargo.tweetreader.adapter.viewholder.TweetViewHolder
import com.alancamargo.tweetreader.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extensions.hasLink

abstract class AdapterHelper(private val viewHolderFactory: ViewHolderFactory) {

    var shareButtonClickListener: ShareButtonClickListener? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    lateinit var inflater: LayoutInflater

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    lateinit var parent: ViewGroup

    fun init(inflater: LayoutInflater, parent: ViewGroup) {
        this.inflater = inflater
        this.parent = parent
    }

    open fun getItemViewType(tweet: Tweet, itemPosition: Int): Int {
        val containsVideo = tweet.containsVideo()
        val containsPhoto = tweet.containsPhoto()
        val containsLink = tweet.fullText.hasLink()
        val hasQuotedTweet = tweet.isQuoting()
        val isRetweet = tweet.isRetweet()
        val isReply = tweet.isReply()

        return when {
            containsPhoto && !containsVideo -> VIEW_TYPE_PHOTO
            containsVideo -> VIEW_TYPE_VIDEO
            containsLink && !hasQuotedTweet -> VIEW_TYPE_LINK
            hasQuotedTweet -> VIEW_TYPE_QUOTED_TWEET
            isRetweet -> VIEW_TYPE_RETWEET
            isReply -> VIEW_TYPE_REPLY
            else -> VIEW_TYPE_TEXT
        }
    }

    open fun getViewHolder(viewType: Int): RecyclerView.ViewHolder {
        viewHolderFactory.init(inflater, parent, shareButtonClickListener)

        return with(viewHolderFactory) {
            when (viewType) {
                VIEW_TYPE_PHOTO -> getPhotoHolder()
                VIEW_TYPE_VIDEO -> getVideoHolder()
                VIEW_TYPE_LINK -> getLinkHolder()
                VIEW_TYPE_QUOTED_TWEET -> getQuoteHolder()
                VIEW_TYPE_RETWEET -> getRetweetHolder()
                VIEW_TYPE_REPLY -> getReplyHolder()
                else -> getTweetHolder()
            }
        }
    }

    open fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, tweet: Tweet) {
        if (viewHolder is QuotedTweetViewHolder) {
            with(viewHolder) {
                bindTo(tweet)
                tweet.quotedTweet?.let(::bindQuotedTweet)
            }
        } else if (viewHolder is TweetViewHolder) {
            viewHolder.bindTo(tweet)
        }
    }

    protected companion object {
        const val VIEW_TYPE_TEXT = 0
        const val VIEW_TYPE_PHOTO = 1
        const val VIEW_TYPE_VIDEO = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_QUOTED_TWEET = 4
        const val VIEW_TYPE_RETWEET = 5
        const val VIEW_TYPE_REPLY = 6
    }

}