package com.alancamargo.tweetreader.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.ui.entities.UiTweet
import com.alancamargo.tweetreader.ui.tools.SharingHelper
import kotlinx.coroutines.launch

class TweetViewModel(
        private val repository: TweetRepository,
        private val sharingHelper: SharingHelper
) : ViewModel() {

    private val tweetsLiveData = MutableLiveData<Result<List<UiTweet>>>()
    private val searchLiveData = MutableLiveData<Result<List<UiTweet>>>()

    fun getTweets(
            hasScrolledToBottom: Boolean,
            isRefreshing: Boolean
    ): LiveData<Result<List<UiTweet>>> {
        return tweetsLiveData.apply {
            viewModelScope.launch {
                val result = repository.getTweets(hasScrolledToBottom, isRefreshing)
                postValue(result)
            }
        }
    }

    fun searchTweets(query: String): LiveData<Result<List<UiTweet>>> {
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

    suspend fun getShareIntent(tweet: UiTweet): Result<Intent> = sharingHelper.getShareIntent(tweet)

}