package com.alancamargo.tweetreader.repository

import android.content.Context
import android.util.Log
import com.alancamargo.tweetreader.api.ApiClient
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.model.Tweet

class TweetRepository(private val context: Context) {

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): List<Tweet> {
        return try {
            getApi().getTweets(maxId = maxId, sinceId = sinceId).map {
                it.also { tweet ->
                    if (tweet.isReply())
                        tweet.repliedTweet = loadRepliedTweet(it)
                }
            }
        } catch (ex: Exception) {
            Log.e(javaClass.simpleName, ex.message, ex)
            emptyList()
        }
    }

    private suspend fun getApi(): TwitterApi {
        val token = TokenHelper().getAccessToken(context)
        return ApiClient.getService(token)
    }

    private suspend fun loadRepliedTweet(tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            getApi().getTweet(id)
        }
    }

}