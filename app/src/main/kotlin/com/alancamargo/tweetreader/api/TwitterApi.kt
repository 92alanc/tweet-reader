package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.BuildConfig.USER_ID
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.alancamargo.tweetreader.model.api.OAuth2Token
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TwitterApi {

    @FormUrlEncoded
    @POST("/oauth2/token")
    fun postCredentials(@Header(AUTHORISATION_HEADER) authorisation: String,
                        @Field("grant_type") grantType: String = "client_credentials"): Call<OAuth2Token>

    @GET("/1.1/statuses/user_timeline.json")
    fun getTweets(@Header(AUTHORISATION_HEADER) authorisation: String,
                  @Query(USER_ID_PARAM) userId: String = USER_ID,
                  @Query("include_rts") includeRetweets: Boolean = false,
                  @Query("exclude_replies") excludeReplies: Boolean = true,
                  @Query("tweet_mode") tweetMode: String = "extended"): Call<List<Tweet>>

    @GET("/1.1/users/show.json")
    fun getUserDetails(@Header(AUTHORISATION_HEADER) authorisation: String,
                       @Query(USER_ID_PARAM) userId: String = USER_ID): Call<User>

    companion object {
        fun getService(): TwitterApi {
            return Retrofit.Builder()
                .baseUrl(DependencyInjection.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TwitterApi::class.java)
        }
    }

}