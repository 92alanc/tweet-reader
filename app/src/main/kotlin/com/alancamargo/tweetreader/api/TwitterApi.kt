package com.alancamargo.tweetreader.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterApi {

    // TODO: add headers
    @GET("/1.1/statuses/user_timeline.json?user_id={user_id}")
    fun getTweets(@Query("user_id") userId: String)

}