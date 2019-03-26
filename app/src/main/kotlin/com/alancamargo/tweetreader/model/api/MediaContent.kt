package com.alancamargo.tweetreader.model.api

import com.google.gson.annotations.SerializedName

data class MediaContent(
    val type: String,
    @SerializedName("media_url_https") val photoUrl: String,
    @SerializedName("video_info") val videoInfo: VideoInfo?
)