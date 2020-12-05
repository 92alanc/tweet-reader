package com.alancamargo.tweetreader.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.ui.adapter.helpers.AdapterHelper
import com.alancamargo.tweetreader.ui.entities.UiTweet
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener

class TweetAdapter(
    private val adapterHelper: AdapterHelper
) : ListAdapter<UiTweet, RecyclerView.ViewHolder>(DiffCallback) {

    private val skippedTweets = mutableMapOf<Int, UiTweet>()

    fun setShareButtonClickListener(shareButtonClickListener: ShareButtonClickListener) {
        adapterHelper.shareButtonClickListener = shareButtonClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        adapterHelper.init(inflater, parent)
        return adapterHelper.getViewHolder(viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tweet = getTweet(position)
        adapterHelper.bindViewHolder(holder, tweet)
    }

    override fun getItemViewType(position: Int): Int {
        val tweet = getTweet(position)
        return adapterHelper.getItemViewType(tweet, position)
    }

    private fun getTweet(position: Int): UiTweet {
        val tweet = if (skippedTweets.contains(position - 1))
            skippedTweets[position - 1]
        else
            getItem(position)

        if (tweet != null)
            return tweet
        else
            throw IllegalStateException("Tweet must not be null")
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<UiTweet>() {
        override fun areItemsTheSame(oldItem: UiTweet, newItem: UiTweet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiTweet, newItem: UiTweet): Boolean {
            return oldItem == newItem
        }
    }

}