package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.api.CONTENT_TYPE_MP4
import com.google.gson.annotations.SerializedName

data class Media(@SerializedName("media") val contents: List<MediaContent>?) {

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