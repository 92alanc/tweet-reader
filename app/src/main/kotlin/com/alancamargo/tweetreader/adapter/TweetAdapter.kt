package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.api.MEDIA_PHOTO
import com.alancamargo.tweetreader.api.MEDIA_VIDEO
import com.alancamargo.tweetreader.model.Tweet

class TweetAdapter : ListAdapter<Tweet, TweetViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_PHOTO -> {
                val itemView = inflater.inflate(R.layout.item_tweet_photo, parent, false)
                PhotoTweetViewHolder(itemView)
            }

            VIEW_TYPE_VIDEO -> {
                val itemView = inflater.inflate(R.layout.item_tweet_video, parent, false)
                VideoTweetViewHolder(itemView)
            }

            else -> {
                val itemView = inflater.inflate(R.layout.item_tweet, parent, false)
                TweetViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = getItem(position)
        holder.bindTo(tweet)
    }

    override fun getItemViewType(position: Int): Int {
        val tweet = getItem(position)

        val containsPhoto = tweet.media?.contents?.any { it.type == MEDIA_PHOTO } ?: false
        val containsVideo = tweet.media?.contents?.any { it.type == MEDIA_VIDEO } ?: false

        return when {
            containsPhoto -> VIEW_TYPE_PHOTO
            containsVideo -> VIEW_TYPE_VIDEO
            else -> VIEW_TYPE_TEXT
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
        private const val VIEW_TYPE_TEXT = 0
        private const val VIEW_TYPE_PHOTO = 1
        private const val VIEW_TYPE_VIDEO = 2

        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.author.id == newItem.author.id &&
                    oldItem.creationDate == newItem.creationDate &&
                    oldItem.text == newItem.text
        }
    }

}