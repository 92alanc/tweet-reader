package com.alancamargo.tweetreader.model.api

import com.squareup.moshi.Json

data class MediaContent(
    val type: String,
    @Json(name = "media_url_https") val photoUrl: String,
    @Json(name = "video_info") val videoInfo: VideoInfo?
)