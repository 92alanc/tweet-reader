package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.model.Tweet

interface SingleTweetCallback {
    fun onTweetLoaded(tweet: Tweet)
    fun onError()
}