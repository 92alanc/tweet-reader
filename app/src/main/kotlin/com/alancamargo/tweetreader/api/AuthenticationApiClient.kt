package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.di.DependencyInjection
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthenticationApiClient {

    fun getService(): AuthenticationApi = lazy {
        Retrofit.Builder()
            .baseUrl(DependencyInjection.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthenticationApi::class.java)
    }.value

}