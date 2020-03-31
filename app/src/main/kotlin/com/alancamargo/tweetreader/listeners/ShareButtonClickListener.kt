package com.alancamargo.tweetreader.listeners

import com.alancamargo.tweetreader.model.Tweet

interface ShareButtonClickListener {
    suspend fun onShareButtonClicked(tweet: Tweet)
}