package com.alancamargo.tweetreader.api.token

interface TokenHelper {
    suspend fun getAccessTokenAndUpdateCache(): String
}