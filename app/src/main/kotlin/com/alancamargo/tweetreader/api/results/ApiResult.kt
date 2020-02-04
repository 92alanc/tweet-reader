package com.alancamargo.tweetreader.api.results

import com.alancamargo.tweetreader.model.api.ErrorResponse

sealed class ApiResult<out T> {
    data class Success<out T>(val body: T) : ApiResult<T>()

    data class GenericError(
        val code: Int? = null,
        val body: ErrorResponse? = null
    ) : ApiResult<Nothing>()

    object NetworkError : ApiResult<Nothing>()
}