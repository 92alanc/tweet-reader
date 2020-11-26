package com.alancamargo.tweetreader.data.tools

interface PreferenceHelper {
    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String)
}