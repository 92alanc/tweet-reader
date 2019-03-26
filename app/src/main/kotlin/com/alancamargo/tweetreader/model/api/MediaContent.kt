package com.alancamargo.tweetreader.model.api

import com.google.gson.annotations.SerializedName

data class MediaContent(
    val url: String,
    val type: String,
    @SerializedName("video_info") val videoInfo: VideoInfo?
)