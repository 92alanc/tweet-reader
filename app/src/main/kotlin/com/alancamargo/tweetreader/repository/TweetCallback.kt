package com.alancamargo.tweetreader.repository

import androidx.lifecycle.LiveData
import com.alancamargo.tweetreader.model.Tweet

interface TweetCallback {
    fun onTweetsFound(tweets: LiveData<List<Tweet>?>)
    fun onAccountSuspended()
}