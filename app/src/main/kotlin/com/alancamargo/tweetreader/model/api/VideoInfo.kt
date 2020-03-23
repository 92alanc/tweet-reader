package com.alancamargo.tweetreader.model.api

import com.squareup.moshi.Json

data class VideoInfo(
    @field:Json(name = "aspect_ratio") val aspectRatio: List<Int>,
    val variants: List<VideoVariant>
)