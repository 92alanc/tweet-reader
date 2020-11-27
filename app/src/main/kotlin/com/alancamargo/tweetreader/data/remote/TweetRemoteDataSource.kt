package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.framework.entities.TweetResponse
import java.io.InputStream

interface TweetRemoteDataSource {
    suspend fun getTweets(maxId: Long?, sinceId: Long?): List<TweetResponse>
    suspend fun searchTweets(query: String): List<TweetResponse>
    suspend fun downloadMedia(mediaUrl: String): InputStream
}