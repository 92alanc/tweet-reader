package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.databinding.ItemTweetBinding
import com.alancamargo.tweetreader.model.Tweet

class TweetAdapter : ListAdapter<Tweet, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_TWEET) {
            val binding = DataBindingUtil.inflate<ItemTweetBinding>(inflater, R.layout.item_tweet,
                parent, false)
            TweetViewHolder(binding)
        } else {
            inflater.inflate(R.layout.item_ad, parent, false).let(::AdViewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isAdPosition(position)) {
            val tweet = getItem(position)
            (holder as TweetViewHolder).bindTo(tweet)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isAdPosition(position)) VIEW_TYPE_AD else VIEW_TYPE_TWEET
    }

    private fun isAdPosition(position: Int) = (position != 0) && ((position + 1) % 10 == 0)

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
        private const val VIEW_TYPE_TWEET = 0
        private const val VIEW_TYPE_AD = 1

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