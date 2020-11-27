package com.alancamargo.tweetreader.framework.entities

import com.squareup.moshi.Json

data class MediaContentResponse(
    val type: String,
    @field:Json(name = "media_url_https") val photoUrl: String,
    @field:Json(name = "video_info") val videoInfo: VideoInfoResponse?
)