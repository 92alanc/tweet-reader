package com.alancamargo.tweetreader.api.tools

interface TokenHelper {
    suspend fun getAccessToken(): String
}