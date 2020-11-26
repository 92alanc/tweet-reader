package com.alancamargo.tweetreader.ui.listeners

import com.alancamargo.tweetreader.framework.entities.Tweet

interface ShareButtonClickListener {
    suspend fun onShareButtonClicked(tweet: Tweet)
}