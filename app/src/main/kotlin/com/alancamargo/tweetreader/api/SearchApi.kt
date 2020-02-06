package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.api.SearchBody
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchApi {

    // FIXME: Error 422
    @POST("1.1/tweets/search/30day/dev.json")
    suspend fun search(@Query("query") searchBody: SearchBody): List<Tweet>

}