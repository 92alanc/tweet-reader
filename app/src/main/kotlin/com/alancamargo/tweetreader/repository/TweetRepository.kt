package com.alancamargo.tweetreader.repository

import android.content.Intent
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.model.Tweet

interface TweetRepository {
    suspend fun getTweets(hasScrolledToBottom: Boolean, isRefreshing: Boolean): Result<List<Tweet>>
    suspend fun searchTweets(query: String): Result<List<Tweet>>
    suspend fun clearCache()
    suspend fun getShareIntent(tweet: Tweet): Result<Intent>
}