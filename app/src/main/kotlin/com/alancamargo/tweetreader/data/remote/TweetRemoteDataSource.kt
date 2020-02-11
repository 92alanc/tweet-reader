package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.model.Tweet

interface TweetRemoteDataSource {
    suspend fun getTweets(maxId: Long?, sinceId: Long?): List<Tweet>
    suspend fun searchTweets(query: String): List<Tweet>
}