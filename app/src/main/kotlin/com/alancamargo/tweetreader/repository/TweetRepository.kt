package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.ApiClient
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.model.Tweet
import com.crashlytics.android.Crashlytics
import retrofit2.HttpException

class TweetRepository(private val tokenHelper: TokenHelper) {

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): List<Tweet> {
        return try {
            getApi().getTweets(maxId = maxId, sinceId = sinceId).map {
                it.also { tweet ->
                    if (tweet.isReply())
                        tweet.repliedTweet = loadRepliedTweet(it)
                }
            }
        } catch (ex: HttpException) {
            Crashlytics.logException(ex)
            emptyList()
        }
    }

    private suspend fun getApi(): TwitterApi {
        val token = tokenHelper.getAccessTokenAndUpdateCache()
        return ApiClient.getService(token)
    }

    private suspend fun loadRepliedTweet(tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            getApi().getTweet(id)
        }
    }

}