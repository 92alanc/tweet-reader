package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.repository.TwitterCallback

class TweetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TweetRepository(application)

    fun insert(tweet: Tweet) {
        repository.insert(tweet)
    }

    fun getTweets(callback: TwitterCallback, maxId: Long? = null, sinceId: Long? = null) {
        repository.select(callback, maxId, sinceId)
    }

}