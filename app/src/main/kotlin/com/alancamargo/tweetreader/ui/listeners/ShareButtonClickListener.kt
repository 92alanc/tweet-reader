package com.alancamargo.tweetreader.ui.listeners

import com.alancamargo.tweetreader.domain.entities.Tweet

interface ShareButtonClickListener {
    suspend fun onShareButtonClicked(tweet: Tweet)
}