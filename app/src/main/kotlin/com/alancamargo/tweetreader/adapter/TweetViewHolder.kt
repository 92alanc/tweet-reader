package com.alancamargo.tweetreader.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.databinding.ItemTweetBinding
import com.alancamargo.tweetreader.model.Tweet

class TweetViewHolder(private val binding: ItemTweetBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(tweet: Tweet) {
        binding.tweet = tweet
        binding.executePendingBindings()
    }

}