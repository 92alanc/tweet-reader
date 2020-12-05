package com.alancamargo.tweetreader.data.local

import android.net.Uri
import com.alancamargo.tweetreader.domain.entities.Tweet
import java.io.InputStream

interface TweetLocalDataSource {
    suspend fun getTweets(): List<Tweet>
    suspend fun updateCache(tweets: List<Tweet>)
    suspend fun clearCache()
    suspend fun getFileUri(byteStream: InputStream, id: String, fileType: FileType): Uri
}