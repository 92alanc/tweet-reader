package com.alancamargo.tweetreader.data.repository

import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.domain.entities.Tweet

interface TweetRepository {
    suspend fun getTweets(hasScrolledToBottom: Boolean, isRefreshing: Boolean): Result<List<Tweet>>
    suspend fun searchTweets(query: String): Result<List<Tweet>>
    suspend fun clearCache()
}