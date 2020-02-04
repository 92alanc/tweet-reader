package com.alancamargo.tweetreader.api.tools

import com.alancamargo.tweetreader.api.results.ApiResult
import com.alancamargo.tweetreader.model.api.ErrorResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(apiCall.invoke())
        } catch (t: Throwable) {
            when (t) {
                is IOException -> ApiResult.NetworkError
                is HttpException -> {
                    val code = t.code()
                    val body = convertErrorBody(t)
                    ApiResult.GenericError(code, body)
                }
                else -> ApiResult.GenericError(null, null)
            }
        }
    }
}

private fun convertErrorBody(httpException: HttpException): ErrorResponse? {
    return try {
        httpException.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (t: Throwable) {
        null
    }
}