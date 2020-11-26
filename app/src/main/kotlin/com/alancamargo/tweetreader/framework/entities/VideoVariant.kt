package com.alancamargo.tweetreader.framework.entities

import com.squareup.moshi.Json

data class VideoVariant(
    @field:Json(name = "content_type") val contentType: String,
    val url: String
)