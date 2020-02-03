package com.alancamargo.tweetreader.api

import com.alancamargo.tweetreader.BuildConfig.CONSUMER_KEY
import com.alancamargo.tweetreader.BuildConfig.CONSUMER_SECRET
import com.alancamargo.tweetreader.util.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Credentials

class TokenHelperImpl(private val preferenceHelper: PreferenceHelper) : TokenHelper {

    override suspend fun getAccessTokenAndUpdateCache(): String {
        val cachedToken = preferenceHelper.getAccessToken()

        return if (cachedToken.isNullOrEmpty()) {
            val tempCredentials = Credentials.basic(
                CONSUMER_KEY,
                CONSUMER_SECRET
            )

            val authClient = AuthenticationApiClient.getService()

            withContext(Dispatchers.IO) {
                authClient.postCredentials(tempCredentials).getAuthorisationHeader().also { newToken ->
                    preferenceHelper.setAccessToken(newToken)
                }
            }
        } else {
            cachedToken
        }
    }

}