package com.alancamargo.tweetreader.api.provider

import com.alancamargo.tweetreader.api.TIMEOUT
import com.alancamargo.tweetreader.api.TokenHelper
import com.alancamargo.tweetreader.api.TokenInterceptor
import com.alancamargo.tweetreader.api.TwitterApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiProvider(private val baseUrl: String, private val tokenHelper: TokenHelper) {

    suspend fun getTwitterApi(): TwitterApi {
        val token = tokenHelper.getAccessTokenAndUpdateCache()
        return getService(token)
    }

    private fun getService(token: String): TwitterApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TwitterApi::class.java)
    }

    private fun buildClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(token))
            .build()
    }

}