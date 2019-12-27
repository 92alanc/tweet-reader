package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alancamargo.tweetreader.repository.TweetRepository

class TweetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TweetRepository(application)

    suspend fun getTweets(
        maxId: Long? = null,
        sinceId: Long? = null
    ) = repository.getTweets(maxId, sinceId)

    suspend fun getTweet(id: Long) = repository.getTweet(id)

}