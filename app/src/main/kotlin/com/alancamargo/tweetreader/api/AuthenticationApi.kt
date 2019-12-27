package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.model.api.OAuth2Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationApi {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun postCredentials(
        @Header(AUTHORISATION_HEADER) token: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): OAuth2Token

}