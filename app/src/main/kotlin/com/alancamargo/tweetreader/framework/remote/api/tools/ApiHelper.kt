package com.alancamargo.tweetreader.framework.remote.api.tools

import android.util.Log
import com.alancamargo.tweetreader.data.entities.Result
import com.alancamargo.tweetreader.data.remote.CODE_ACCOUNT_SUSPENDED
import com.alancamargo.tweetreader.data.entities.ErrorResponse
import com.alancamargo.tweetreader.data.tools.Logger
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ApiHelper(private val logger: Logger) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return safeApiCall(apiCall, null)
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
        alternative: (suspend () -> T)? = null
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (t: Throwable) {
                Log.e(javaClass.simpleName, t.message, t)
                logger.logException(t)
                val apiError = getApiError(t)

                if (alternative != null)
                    tryRunAlternative(apiError, alternative)
                else
                    apiError
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
            logger.logException(t)
            originalApiError
        }
    }

}
