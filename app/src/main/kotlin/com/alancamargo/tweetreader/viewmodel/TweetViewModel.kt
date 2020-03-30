package com.alancamargo.tweetreader.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.TweetRepository
import kotlinx.coroutines.launch

class TweetViewModel(private val repository: TweetRepository) : ViewModel() {

    private val tweetsLiveData = MutableLiveData<Result<List<Tweet>>>()
    private val searchLiveData = MutableLiveData<Result<List<Tweet>>>()

    fun getTweets(
        hasScrolledToBottom: Boolean,
        isRefreshing: Boolean
    ): LiveData<Result<List<Tweet>>> {
        return tweetsLiveData.apply {
            viewModelScope.launch {
                val result = repository.getTweets(hasScrolledToBottom, isRefreshing)
                postValue(result)
            }
        }
    }

    fun searchTweets(query: String): LiveData<Result<List<Tweet>>> {
        return searchLiveData.apply {
            viewModelScope.launch {
                val result = repository.searchTweets(query)
                postValue(result)
            }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearCache()
        }
    }

    suspend fun getShareIntent(tweet: Tweet): Result<Intent> = repository.getShareIntent(tweet)

}