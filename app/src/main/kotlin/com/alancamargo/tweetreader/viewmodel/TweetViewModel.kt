package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TweetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TweetRepository(application)

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): LiveData<List<Tweet>?> {
        return withContext(Dispatchers.IO) {
            repository.getTweets(maxId, sinceId)
        }
    }

}