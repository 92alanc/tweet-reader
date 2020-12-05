package com.alancamargo.tweetreader.ui.listeners

import com.alancamargo.tweetreader.ui.entities.UiTweet

interface ShareButtonClickListener {
    suspend fun onShareButtonClicked(tweet: UiTweet)
}