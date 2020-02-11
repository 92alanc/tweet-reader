package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.api.tools.ApiHelper
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.model.Tweet

class TweetRepository(
    private val localDataSource: TweetLocalDataSource,
    private val remoteDataSource: TweetRemoteDataSource,
    private val apiHelper: ApiHelper
) {

    private var tweets: List<Tweet> = emptyList()

    suspend fun getTweets(
        hasScrolledToBottom: Boolean,
        isRefreshing: Boolean
    ): Result<List<Tweet>> {
        val maxId = if (hasScrolledToBottom) tweets.getMaxId() else null
        val sinceId = if (isRefreshing) tweets.getSinceId() else null

        return apiHelper.safeApiCall (apiCall = {
            val newTweets = remoteDataSource.getTweets(maxId = maxId, sinceId = sinceId)
            localDataSource.updateCache(newTweets)
            tweets = tweets.append(newTweets, isRefreshing)
            tweets
        }, alternative = {
            localDataSource.getTweets()
        })
    }

    suspend fun searchTweets(query: String): Result<List<Tweet>> = apiHelper.safeApiCall {
        remoteDataSource.searchTweets(query)
    }

    private fun List<Tweet>.append(newTweets: List<Tweet>, isRefreshing: Boolean): List<Tweet> {
        return if (isRefreshing)
            newTweets + this
        else
            this + newTweets
    }

    private fun List<Tweet>.getMaxId(): Long? = if (isEmpty()) null else last().id - 1

    private fun List<Tweet>.getSinceId(): Long? = if (isEmpty()) null else first().id

}