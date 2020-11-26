package com.alancamargo.tweetreader.data.remote

import com.alancamargo.tweetreader.framework.entities.Tweet
import java.io.InputStream

interface TweetRemoteDataSource {
    suspend fun getTweets(maxId: Long?, sinceId: Long?): List<Tweet>
    suspend fun searchTweets(query: String): List<Tweet>
    suspend fun downloadMedia(mediaUrl: String): InputStream
}