package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.repository.TwitterCallback

class TweetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TweetRepository(application)

    fun insert(tweet: Tweet) {
        repository.insert(tweet)
    }

    fun getTweets(lifecycleOwner: LifecycleOwner,
                  callback: TwitterCallback,
                  maxId: Long? = null,
                  sinceId: Long? = null) {
        repository.select(lifecycleOwner, callback, maxId, sinceId)
    }

}