package com.alancamargo.tweetreader.data.tools

interface TokenHelper {
    suspend fun getAccessToken(): String
}