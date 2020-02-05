package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.model.Tweet
import retrofit2.http.POST

interface SearchApi {

    @POST("1.1/tweets/search/30day/dev.json")
    suspend fun search(
        
    ): List<Tweet>

}