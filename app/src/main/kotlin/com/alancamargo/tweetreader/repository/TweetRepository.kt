package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.TwitterApi
import com.alancamargo.tweetreader.api.provider.ApiProvider
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.api.tools.safeApiCall
import com.alancamargo.tweetreader.model.Tweet

class TweetRepository(private val apiProvider: ApiProvider) {

    private var tweets: List<Tweet> = emptyList()

    suspend fun getTweets(
        hasScrolledToBottom: Boolean,
        isRefreshing: Boolean
    ): Result<List<Tweet>> {
        val api = apiProvider.getTwitterApi()

        val maxId = if (hasScrolledToBottom) tweets.getMaxId() else null
        val sinceId = if (isRefreshing) tweets.getSinceId() else null

        return safeApiCall {
            val newTweets = api.getTweets(maxId = maxId, sinceId = sinceId).map {
                it.also { tweet ->
                    if (tweet.isReply())
                        tweet.repliedTweet = loadRepliedTweet(api, it)
                }
            }

            tweets = updateTweets(newTweets, isRefreshing)

            tweets
        }
    }

    private suspend fun loadRepliedTweet(api: TwitterApi, tweet: Tweet): Tweet? {
        return tweet.inReplyTo?.let { id ->
            api.getTweet(id)
        }
    }

    private fun updateTweets(newTweets: List<Tweet>, isRefreshing: Boolean): List<Tweet> {
        return if (isRefreshing)
            newTweets + tweets
        else
            tweets + newTweets
    }

    private fun List<Tweet>.getMaxId(): Long? = if (isEmpty()) null else last().id - 1

    private fun List<Tweet>.getSinceId(): Long? = if (isEmpty()) null else first().id

}