package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.model.api.SearchBody
import com.alancamargo.tweetreader.model.api.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {

    @POST("1.1/tweets/search/30day/dev.json")
    suspend fun search(@Body searchBody: SearchBody): SearchResponse

}