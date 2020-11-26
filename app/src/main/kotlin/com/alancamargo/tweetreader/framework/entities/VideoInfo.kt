package com.alancamargo.tweetreader.framework.entities

import com.squareup.moshi.Json

data class VideoInfo(
    @field:Json(name = "aspect_ratio") val aspectRatio: List<Int>,
    val variants: List<VideoVariant>
)