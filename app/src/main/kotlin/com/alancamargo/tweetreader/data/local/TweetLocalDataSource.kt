package com.alancamargo.tweetreader.data.local

import android.net.Uri
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import java.io.InputStream

interface TweetLocalDataSource {
    suspend fun getTweets(): List<TweetResponse>
    suspend fun updateCache(tweets: List<TweetResponse>)
    suspend fun clearCache()
    suspend fun getFileUri(byteStream: InputStream, id: String, fileType: FileType): Uri
}