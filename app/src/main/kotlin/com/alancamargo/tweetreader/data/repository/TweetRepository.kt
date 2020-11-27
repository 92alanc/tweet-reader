package com.alancamargo.tweetreader.data.repository

import android.content.Intent
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.framework.entities.TweetResponse

interface TweetRepository {
    suspend fun getTweets(hasScrolledToBottom: Boolean, isRefreshing: Boolean): Result<List<TweetResponse>>
    suspend fun searchTweets(query: String): Result<List<TweetResponse>>
    suspend fun clearCache()
    suspend fun getShareIntent(tweet: TweetResponse): Result<Intent>
}