package com.alancamargo.tweetreader.data.local

import com.alancamargo.tweetreader.model.Tweet

interface TweetLocalDataSource {
    suspend fun getTweets(): List<Tweet>
    suspend fun updateCache(tweets: List<Tweet>)
    suspend fun clearCache()
}