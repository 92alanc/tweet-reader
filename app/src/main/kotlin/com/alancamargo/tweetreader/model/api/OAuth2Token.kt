package com.alancamargo.tweetreader.model.api

import com.google.gson.annotations.SerializedName

data class OAuth2Token(
    @SerializedName("token_type") private val tokenType: String,
    @SerializedName("access_token") private val accessToken: String
) {

    fun getAuthorisationHeader() = "$tokenType $accessToken"

}