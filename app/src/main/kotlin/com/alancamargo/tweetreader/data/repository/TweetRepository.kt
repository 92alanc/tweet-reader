package com.alancamargo.tweetreader.data.repository

import android.content.Intent
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.framework.entities.Tweet

interface TweetRepository {
    suspend fun getTweets(hasScrolledToBottom: Boolean, isRefreshing: Boolean): Result<List<Tweet>>
    suspend fun searchTweets(query: String): Result<List<Tweet>>
    suspend fun clearCache()
    suspend fun getShareIntent(tweet: Tweet): Result<Intent>
}