package com.alancamargo.tweetreader.framework.repository

import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.data.remote.TweetRemoteDataSource
import com.alancamargo.tweetreader.data.repository.TweetRepository
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.framework.remote.api.tools.ApiHelper

class TweetRepositoryImpl(
    private val localDataSource: TweetLocalDataSource,
    private val remoteDataSource: TweetRemoteDataSource,
    private val apiHelper: ApiHelper
) : TweetRepository {

    private var tweets: List<Tweet> = emptyList()

    override suspend fun getTweets(
        hasScrolledToBottom: Boolean,
        isRefreshing: Boolean
    ): Result<List<Tweet>> {
        val maxId = if (hasScrolledToBottom) tweets.getMaxId() else null
        val sinceId = if (isRefreshing) tweets.getSinceId() else null

        return apiHelper.safeApiCall(apiCall = {
            val newTweets = remoteDataSource.getTweets(maxId = maxId, sinceId = sinceId).filterNot {
                this.tweets.contains(it)
            }

            localDataSource.updateCache(newTweets)
            tweets = tweets.append(newTweets, isRefreshing)
            tweets
        }, alternative = {
            localDataSource.getTweets()
        })
    }

    override suspend fun searchTweets(query: String): Result<List<Tweet>> = apiHelper.safeApiCall {
        remoteDataSource.searchTweets(query)
    }

    override suspend fun clearCache() {
        localDataSource.clearCache()
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