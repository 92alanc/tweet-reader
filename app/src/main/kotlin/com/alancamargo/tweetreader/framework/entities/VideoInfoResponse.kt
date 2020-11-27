package com.alancamargo.tweetreader.framework.entities

import com.squareup.moshi.Json

data class VideoInfoResponse(
    @field:Json(name = "aspect_ratio") val aspectRatio: List<Int>,
    val variants: List<VideoVariantResponse>
)