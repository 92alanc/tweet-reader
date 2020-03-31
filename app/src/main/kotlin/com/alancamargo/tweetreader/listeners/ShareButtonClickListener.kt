package com.alancamargo.tweetreader.listeners

import com.alancamargo.tweetreader.model.Tweet

interface ShareButtonClickListener {
    fun onShareButtonClicked(tweet: Tweet)
}