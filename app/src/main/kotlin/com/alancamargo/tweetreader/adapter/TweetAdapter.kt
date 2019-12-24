package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.viewholder.*
import com.alancamargo.tweetreader.api.MEDIA_PHOTO
import com.alancamargo.tweetreader.api.MEDIA_VIDEO
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.hasLink

class TweetAdapter : ListAdapter<Tweet, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return with(inflater) {
            when (viewType) {
                VIEW_TYPE_PHOTO -> getPhotoHolder(parent)
                VIEW_TYPE_VIDEO -> getVideoHolder(parent)
                VIEW_TYPE_LINK -> getLinkHolder(parent)
                VIEW_TYPE_QUOTED_TWEET -> getQuoteHolder(parent)
                VIEW_TYPE_RETWEET -> getRetweetHolder(parent)
                VIEW_TYPE_REPLY -> getReplyHolder(parent)
                VIEW_TYPE_AD -> getAdHolder(parent)
                else -> getTweetHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TweetViewHolder) {
            val tweet = getItem(position)
            if (holder is QuotedTweetViewHolder) {
                holder.run {
                    bindTo(tweet)
                    bindQuotedTweet()
                }
            } else {
                holder.bindTo(tweet)
            }
        } else if (holder is AdViewHolder) {
            holder.loadAd()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val tweet = getItem(position)

        val containsPhoto = tweet.media?.contents?.any { it.type == MEDIA_PHOTO } ?: false
        val containsVideo = tweet.media?.contents?.any { it.type == MEDIA_VIDEO } ?: false
        val containsLink = tweet.text.hasLink()
        val hasQuotedTweet = tweet.quotedTweet != null
        val isRetweet = tweet.retweet != null
        val isReply = tweet.inReplyTo != null
        val isAd = position % 5 == 0

        return when {
            containsPhoto -> VIEW_TYPE_PHOTO
            containsVideo -> VIEW_TYPE_VIDEO
            containsLink && !hasQuotedTweet -> VIEW_TYPE_LINK
            hasQuotedTweet -> VIEW_TYPE_QUOTED_TWEET
            isRetweet -> VIEW_TYPE_RETWEET
            isReply -> VIEW_TYPE_REPLY
            isAd -> VIEW_TYPE_AD
            else -> VIEW_TYPE_TEXT
        }
    }

    private fun LayoutInflater.getPhotoHolder(parent: ViewGroup): PhotoTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_photo, parent, false)
        return PhotoTweetViewHolder(itemView)
    }

    private fun LayoutInflater.getVideoHolder(parent: ViewGroup): VideoTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_video, parent, false)
        return VideoTweetViewHolder(itemView)
    }

    private fun LayoutInflater.getLinkHolder(parent: ViewGroup): LinkTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_link, parent, false)
        return LinkTweetViewHolder(itemView)
    }

    private fun LayoutInflater.getQuoteHolder(parent: ViewGroup): QuotedTweetViewHolder {
        val itemView = inflate(R.layout.item_quoted_tweet, parent, false)
        return QuotedTweetViewHolder(itemView)
    }

    private fun LayoutInflater.getRetweetHolder(parent: ViewGroup): RetweetViewHolder {
        val itemView = inflate(R.layout.item_retweet, parent, false)
        return RetweetViewHolder(itemView)
    }

    private fun LayoutInflater.getReplyHolder(parent: ViewGroup): ReplyViewHolder {
        val itemView = inflate(R.layout.item_tweet_reply, parent, false)
        return ReplyViewHolder(itemView)
    }

    private fun LayoutInflater.getAdHolder(parent: ViewGroup): AdViewHolder {
        val itemView = inflate(R.layout.item_ad, parent, false)
        return AdViewHolder(itemView)
    }

    private fun LayoutInflater.getTweetHolder(parent: ViewGroup): TweetViewHolder {
        val itemView = inflate(R.layout.item_tweet, parent, false)
        return TweetViewHolder(itemView)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
        private const val VIEW_TYPE_TEXT = 0
        private const val VIEW_TYPE_PHOTO = 1
        private const val VIEW_TYPE_VIDEO = 2
        private const val VIEW_TYPE_LINK = 3
        private const val VIEW_TYPE_QUOTED_TWEET = 4
        private const val VIEW_TYPE_RETWEET = 5
        private const val VIEW_TYPE_REPLY = 6
        private const val VIEW_TYPE_AD = 7

        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author.id == newItem.author.id &&
                    oldItem.creationDate == newItem.creationDate &&
                    oldItem.text == newItem.text
        }
    }

}