package com.alancamargo.tweetreader.helpers

interface PreferenceHelper {
    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String)
}