package com.alancamargo.tweetreader.ui.listeners

import com.alancamargo.tweetreader.framework.entities.TweetResponse

interface ShareButtonClickListener {
    suspend fun onShareButtonClicked(tweet: TweetResponse)
}