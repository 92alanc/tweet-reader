package com.alancamargo.tweetreader.framework.remote.api

import com.alancamargo.tweetreader.domain.entities.SearchBody
import com.alancamargo.tweetreader.data.entities.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApi {

    @POST("1.1/tweets/search/30day/dev.json")
    suspend fun search(@Body searchBody: SearchBody): SearchResponse

}