package com.alancamargo.tweetreader.framework.remote.api

import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.data.remote.TWEET_MODE_EXTENDED
import com.alancamargo.tweetreader.data.remote.TWEET_MODE_PARAM
import com.alancamargo.tweetreader.data.remote.USER_ID_PARAM
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_TWEET_COUNT = 9

interface TwitterApi {

    @GET("1.1/statuses/user_timeline.json")
    suspend fun getTweets(
        @Query(USER_ID_PARAM) userId: String = BuildConfig.USER_ID,
        @Query(TWEET_MODE_PARAM) tweetMode: String = TWEET_MODE_EXTENDED,
        @Query("count") count: Int = DEFAULT_TWEET_COUNT,
        @Query("max_id") maxId: Long? = null,
        @Query("since_id") sinceId: Long? = null
    ): List<TweetResponse>

    @GET("1.1/statuses/show.json")
    suspend fun getTweet(
        @Query("id") id: Long,
        @Query(TWEET_MODE_PARAM) tweetMode: String = TWEET_MODE_EXTENDED
    ): TweetResponse?

}