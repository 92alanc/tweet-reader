package com.alancamargo.tweetreader.api

interface TokenHelper {
    suspend fun getAccessTokenAndUpdateCache(): String
}