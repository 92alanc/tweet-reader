package com.alancamargo.tweetreader.model.api

import com.google.gson.annotations.SerializedName

data class VideoVariant(
    @SerializedName("content_type") val contentType: String,
    val url: String
)