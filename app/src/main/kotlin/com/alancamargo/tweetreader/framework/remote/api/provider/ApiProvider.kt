package com.alancamargo.tweetreader.framework.remote.api.provider

import com.alancamargo.tweetreader.data.remote.TIMEOUT
import com.alancamargo.tweetreader.data.tools.TokenHelper
import com.alancamargo.tweetreader.framework.remote.api.DownloadApi
import com.alancamargo.tweetreader.framework.remote.api.SearchApi
import com.alancamargo.tweetreader.framework.remote.api.TwitterApi
import com.alancamargo.tweetreader.framework.remote.api.tools.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiProvider(private val baseUrl: String, private val tokenHelper: TokenHelper) {

    suspend fun getTwitterApi(): TwitterApi {
        val token = tokenHelper.getAccessToken()
        return getService(token)
    }

    suspend fun getSearchApi(): SearchApi {
        val token = tokenHelper.getAccessToken()
        return getService(token)
    }

    fun getDownloadApi(): DownloadApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
            .create(DownloadApi::class.java)
    }

    private inline fun <reified T> getService(token: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(buildClient(token))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    private fun buildClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(token))
            .build()
    }

}
