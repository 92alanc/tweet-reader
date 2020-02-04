package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.api.CONTENT_TYPE_MP4
import com.squareup.moshi.Json

data class Media(@field:Json(name = "media") val contents: List<MediaContent>?) {

    fun getPhotoUrls() = contents?.map { it.photoUrl }

    fun getVideoUrl(): String? {
        return contents?.first()
            ?.videoInfo
            ?.variants
            ?.first { variant ->
                variant.contentType == CONTENT_TYPE_MP4
            }
            ?.url
    }

}