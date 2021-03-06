package com.alancamargo.tweetreader.framework.tools

import android.content.Context
import com.alancamargo.tweetreader.data.tools.PreferenceHelper

class PreferenceHelperImpl(context: Context) :
    PreferenceHelper {

    private val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    override fun getAccessToken() = preferences.getString(KEY_TOKEN, null)

    override fun setAccessToken(accessToken: String) {
        preferences.edit().putString(KEY_TOKEN, accessToken).apply()
    }

    private companion object {
        const val KEY_TOKEN = "token"
    }

}