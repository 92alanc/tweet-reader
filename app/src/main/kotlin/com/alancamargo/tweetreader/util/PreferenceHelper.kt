package com.alancamargo.tweetreader.util

interface PreferenceHelper {
    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String)
}