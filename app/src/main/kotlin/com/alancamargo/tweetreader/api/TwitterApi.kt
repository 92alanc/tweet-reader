package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.BuildConfig.BASE_URL
import com.alancamargo.tweetreader.model.api.ApiTweet
import com.alancamargo.tweetreader.model.api.ApiUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterApi {

    // TODO: add headers
    @GET("/1.1/statuses/user_timeline.json?user_id={user_id}")
    fun getTweets(@Query("user_id") userId: Long): Call<List<ApiTweet>>

    // TODO: add headers
    @GET("/1.1/users/show.json?user_id={user_id}")
    fun getUserInfo(@Query("user_id") userId: Long): Call<ApiUser>

    companion object {
        fun getService(): TwitterApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TwitterApi::class.java)
        }
    }

}