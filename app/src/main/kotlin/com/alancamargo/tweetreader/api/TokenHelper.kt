package com.alancamargo.tweetreader.api

import android.content.Context
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_KEY
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_SECRET
import com.alancamargo.tweetreader.util.PreferenceHelper
import okhttp3.Credentials

class TokenHelper {

    suspend fun getAccessToken(context: Context): String {
        val preferenceHelper = PreferenceHelper(context)
        val cachedToken = preferenceHelper.getAccessToken()

        return if (cachedToken.isNullOrEmpty()) {
            val tempCredentials = Credentials.basic(
                CONSUMER_KEY,
                CONSUMER_SECRET
            )

            val authClient = AuthenticationApiClient.getService()
            authClient.postCredentials(tempCredentials).getAuthorisationHeader().also { newToken ->
                preferenceHelper.setAccessToken(newToken)
            }
        } else {
            cachedToken
        }
    }

}