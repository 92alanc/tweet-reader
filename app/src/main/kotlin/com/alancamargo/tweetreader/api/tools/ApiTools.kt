package com.alancamargo.tweetreader.api.tools

import com.alancamargo.tweetreader.api.CODE_ACCOUNT_SUSPENDED
import com.alancamargo.tweetreader.api.results.Result
import com.alancamargo.tweetreader.model.api.ErrorResponse
import com.crashlytics.android.Crashlytics
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
    alternative: suspend () -> T
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall.invoke())
        } catch (t: Throwable) {
            Crashlytics.logException(t)
            val apiError = getApiError(t)
            tryRunAlternative(apiError, alternative)
        }
    }
}

private fun getApiError(t: Throwable): Result<Nothing> {
    return when (t) {
        is IOException -> Result.NetworkError
        is HttpException -> {
            val code = t.code()

            if (code == CODE_ACCOUNT_SUSPENDED) {
                Result.AccountSuspendedError
            } else {
                val body = convertErrorBody(t)
                Result.GenericError(code, body)
            }
        }
        else -> Result.GenericError(null, null)
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

private suspend fun <T> tryRunAlternative(
    originalApiError: Result<Nothing>,
    block: suspend () -> T
): Result<T> {
    return try {
        Result.Success(block.invoke())
    } catch (t: Throwable) {
        Crashlytics.logException(t)
        originalApiError
    }
}