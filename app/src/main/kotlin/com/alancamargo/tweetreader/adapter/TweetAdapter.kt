package com.alancamargo.tweetreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.databinding.ItemTweetBinding
import com.alancamargo.tweetreader.model.Tweet

class TweetAdapter : ListAdapter<Tweet, TweetViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTweetBinding>(inflater, R.layout.item_tweet,
            parent, false)
        return TweetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = getItem(position)
        holder.bindTo(tweet)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
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