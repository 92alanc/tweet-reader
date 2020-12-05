package com.alancamargo.tweetreader.domain.entities

import com.alancamargo.tweetreader.data.remote.CONTENT_TYPE_MP4

data class Media(val contents: List<MediaContent>?) {

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
