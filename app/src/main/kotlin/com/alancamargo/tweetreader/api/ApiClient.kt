package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.di.DependencyInjection
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    fun getService(token: String): MyNewApi = lazy {
        Retrofit.Builder()
            .baseUrl(DependencyInjection.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildClient(token))
            .build()
            .create(MyNewApi::class.java)
    }.value

    private fun buildClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(TokenInterceptor(token))
            .build()
    }

}