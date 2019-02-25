package com.alancamargo.tweetreader.util

import android.content.Context

class PreferenceHelper(context: Context) {

    private val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    fun getAccessToken() = preferences.getString(KEY_TOKEN, "")

    fun setAccessToken(accessToken: String) {
        preferences.edit().putString(KEY_TOKEN, accessToken).apply()
    }

    private companion object {
        const val KEY_TOKEN = "token"
    }

}