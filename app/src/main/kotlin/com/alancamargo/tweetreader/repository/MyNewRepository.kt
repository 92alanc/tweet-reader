package com.alancamargo.tweetreader.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.api.ApiClient
import com.alancamargo.tweetreader.api.MyNewApi
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.model.Tweet

class MyNewRepository(private val context: Context) {

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): LiveData<List<Tweet>> {
        return MutableLiveData<List<Tweet>>().apply {
            value = getApi().getTweets(maxId = maxId, sinceId = sinceId)
        }
    }

    suspend fun getTweet(id: Long): Tweet {
        return getApi().getTweet(id)
    }

    private suspend fun getApi(): MyNewApi {
        val token = TokenHelper().getAccessToken(context)
        return ApiClient.getService(token)
    }

}