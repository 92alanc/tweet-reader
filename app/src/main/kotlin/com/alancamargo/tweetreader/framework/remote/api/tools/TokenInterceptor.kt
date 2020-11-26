package com.alancamargo.tweetreader.framework.remote.api.tools

import com.alancamargo.tweetreader.data.remote.AUTHORISATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header(AUTHORISATION_HEADER, token)
            .build()

        return chain.proceed(request)
    }

}