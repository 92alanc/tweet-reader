package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.model.Tweet
import com.crashlytics.android.Crashlytics

class TweetRepository(private val apiProvider: ApiProvider) {

    suspend fun getTweets(maxId: Long? = null, sinceId: Long? = null): List<Tweet> {
        return try {
            val api = apiProvider.getTwitterApi()
            api.getTweets(maxId = maxId, sinceId = sinceId).map {
                it.also { tweet ->
                    if (tweet.isReply())
                        tweet.repliedTweet = loadRepliedTweet(api, it)
                }
            }
        } catch (t: Throwable) {
            // TODO: handle different error codes
            Crashlytics.logException(t)
            emptyList()
        }
    }

    private suspend fun loadRepliedTweet(api: TwitterApi, tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            api.getTweet(id)
        }
    }

}