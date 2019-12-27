package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alancamargo.tweetreader.api.ApiClient
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.database.TwitterDatabase
import com.alancamargo.tweetreader.model.Tweet

class TweetRepository(private val context: Context) {

    private val database = TwitterDatabase.getInstance(context).tweetDao()

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): LiveData<List<Tweet>> {
        return MutableLiveData<List<Tweet>>().apply {
            val tweets = try {
                getApi().getTweets(maxId = maxId, sinceId = sinceId)
            } catch (ex: Exception) {
                Log.e(javaClass.simpleName, ex.message, ex)
                database.select()
            }

            postValue(tweets)
        }
    }

    suspend fun getTweet(id: Long): LiveData<Tweet> {
        return MutableLiveData<Tweet>().apply {
            val tweet = try {
                getApi().getTweet(id)
            } catch (ex: Exception) {
                Log.e(javaClass.simpleName, ex.message, ex)
                database.select(id)
            }

            postValue(tweet)
        }
    }

    private suspend fun getApi(): TwitterApi {
        val token = TokenHelper().getAccessToken(context)
        return ApiClient.getService(token)
    }

}