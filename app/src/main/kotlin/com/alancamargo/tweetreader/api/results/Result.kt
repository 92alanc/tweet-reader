package com.alancamargo.tweetreader.api.results

import com.alancamargo.tweetreader.model.api.ErrorResponse

sealed class Result<out T> {
    data class Success<out T>(val body: T) : Result<T>()

    data class GenericError(
        val code: Int? = null,
        val body: ErrorResponse? = null
    ) : Result<Nothing>()

    object AccountSuspendedError : Result<Nothing>()

    object NetworkError : Result<Nothing>()
}