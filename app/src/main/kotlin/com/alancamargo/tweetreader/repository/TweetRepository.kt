package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.model.Tweet

interface TweetRepository {
    suspend fun getTweets(hasScrolledToBottom: Boolean, isRefreshing: Boolean): Result<List<Tweet>>
    suspend fun searchTweets(query: String): Result<List<Tweet>>
    suspend fun clearCache()
    fun share(tweet: Tweet)
}