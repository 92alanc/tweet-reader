package com.alancamargo.tweetreader.framework.entities

import com.squareup.moshi.Json

data class VideoVariantResponse(
    @field:Json(name = "content_type") val contentType: String,
    val url: String
)