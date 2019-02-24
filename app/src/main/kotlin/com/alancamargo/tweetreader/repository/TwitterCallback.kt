package com.alancamargo.tweetreader.repository

import androidx.lifecycle.LiveData
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User

interface TwitterCallback {
    fun onTweetsFound(tweets: LiveData<List<Tweet>>)
    fun onUserDetailsFound(userDetails: LiveData<User>)
}