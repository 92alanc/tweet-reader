package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.adapter.viewholder.*
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.listeners.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extensions.hasLink

class TweetAdapter(
    private val imageHandler: ImageHandler,
    private val linkClickListener: LinkClickListener
) : ListAdapter<Tweet, RecyclerView.ViewHolder>(DiffCallback) {

    private val skippedTweets = mutableMapOf<Int, Tweet>()

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
            val tweet = getTweet(position)

            if (holder is QuotedTweetViewHolder) {
                with(holder) {
                    bindTo(tweet)
                    tweet.quotedTweet?.let(::bindQuotedTweet)
                }
            } else {
                holder.bindTo(tweet)
            }
        } else if (holder is AdViewHolder) {
            holder.loadAd()
        }
    }

    override fun getItemViewType(position: Int): Int {
        // Every 5 tweets will be an ad, starting from position 1
        val isAd = (position.toString().endsWith("1"))
                || (position.toString().endsWith("6"))

        if (isAd) {
            return VIEW_TYPE_AD
        } else {
            val tweet = getTweet(position)

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
    }

    private fun getTweet(position: Int): Tweet {
        val tweet = if (skippedTweets.contains(position - 1))
            skippedTweets[position - 1]
        else
            getItem(position)

        if (tweet != null)
            return tweet
        else
            throw IllegalStateException("Tweet must not be null")
    }

    private fun LayoutInflater.getPhotoHolder(parent: ViewGroup): PhotoTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_photo, parent, false)
        return PhotoTweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getVideoHolder(parent: ViewGroup): VideoTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_video, parent, false)
        return VideoTweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getLinkHolder(parent: ViewGroup): LinkTweetViewHolder {
        val itemView = inflate(R.layout.item_tweet_link, parent, false)
        return LinkTweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getQuoteHolder(parent: ViewGroup): QuotedTweetViewHolder {
        val itemView = inflate(R.layout.item_quoted_tweet, parent, false)
        return QuotedTweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getRetweetHolder(parent: ViewGroup): RetweetViewHolder {
        val itemView = inflate(R.layout.item_retweet, parent, false)
        return RetweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getReplyHolder(parent: ViewGroup): ReplyViewHolder {
        val itemView = inflate(R.layout.item_tweet_reply, parent, false)
        return ReplyViewHolder(itemView, imageHandler, linkClickListener)
    }

    private fun LayoutInflater.getAdHolder(parent: ViewGroup): AdViewHolder {
        val itemView = inflate(R.layout.item_ad, parent, false)
        return AdViewHolder(itemView)
    }

    private fun LayoutInflater.getTweetHolder(parent: ViewGroup): TweetViewHolder {
        val itemView = inflate(R.layout.item_tweet, parent, false)
        return TweetViewHolder(itemView, imageHandler, linkClickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
        const val VIEW_TYPE_TEXT = 0
        const val VIEW_TYPE_PHOTO = 1
        const val VIEW_TYPE_VIDEO = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_QUOTED_TWEET = 4
        const val VIEW_TYPE_RETWEET = 5
        const val VIEW_TYPE_REPLY = 6
        const val VIEW_TYPE_AD = 7

        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author.id == newItem.author.id &&
                    oldItem.creationDate == newItem.creationDate &&
                    oldItem.fullText == newItem.fullText &&
                    oldItem.text == newItem.text
        }
    }

}