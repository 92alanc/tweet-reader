package com.alancamargo.tweetreader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.repository.TwitterCallback

class TweetViewModel(context: Context) : ViewModel() {

    private val repository = TweetRepository(context)

    fun insert(tweet: Tweet) {
        repository.insert(tweet)
    }

    fun select(callback: TwitterCallback) {
        repository.select(callback)
    }

}