package com.alancamargo.tweetreader.viewmodel

import com.alancamargo.tweetreader.model.Tweet

interface View {
    fun onAccountSuspended()
    fun onTweetsFound(tweets: List<Tweet>?)
}