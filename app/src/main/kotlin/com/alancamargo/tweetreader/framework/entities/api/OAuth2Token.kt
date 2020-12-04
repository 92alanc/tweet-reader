package com.alancamargo.tweetreader.framework.entities.api

import com.squareup.moshi.Json

data class OAuth2Token(
    @field:Json(name = "token_type") private val tokenType: String,
    @field:Json(name = "access_token") private val accessToken: String
) {

    fun getAuthorisationHeader() = "$tokenType $accessToken"

}