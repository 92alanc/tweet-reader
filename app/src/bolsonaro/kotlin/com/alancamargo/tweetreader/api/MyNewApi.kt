package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.BuildConfig
import com.alancamargo.tweetreader.model.Tweet
import retrofit2.http.GET
import retrofit2.http.Query

interface MyNewApi {

    @GET("1.1/statuses/user_timeline.json")
    suspend fun getTweets(
        @Query(USER_ID_PARAM) userId: String = BuildConfig.USER_ID,
        @Query(TWEET_MODE_PARAM) tweetMode: String = TWEET_MODE_EXTENDED,
        @Query("count") count: Int = 9,
        @Query("max_id") maxId: Long? = null,
        @Query("since_id") sinceId: Long? = null
    ): List<Tweet>

    @GET("1.1/statuses/show.json")
    suspend fun getTweet(
        @Query("id") id: Long,
        @Query(TWEET_MODE_PARAM) tweetMode: String = TWEET_MODE_EXTENDED
    ): Tweet

}