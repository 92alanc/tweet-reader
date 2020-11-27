package com.alancamargo.tweetreader.framework.entities

import android.util.Size
import androidx.room.TypeConverter
import com.alancamargo.tweetreader.data.remote.CONTENT_TYPE_MP4
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

data class MediaResponse(@field:Json(name = "media") val contents: List<MediaContentResponse>?) {

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

    fun getVideoAspectRatio(): Size? {
        val aspectRatio = contents?.first()
            ?.videoInfo
            ?.aspectRatio

        return if (aspectRatio != null)
            Size(aspectRatio.first(), aspectRatio.last())
        else
            null
    }

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val mediaAdapter = moshi.adapter(MediaResponse::class.java)

        @TypeConverter
        fun mediaToString(media: MediaResponse?): String? {
            return if (media != null)
                mediaAdapter.toJson(media)
            else
                null
        }

        @TypeConverter
        fun stringToMedia(string: String?): MediaResponse? {
            return if (string != null)
                mediaAdapter.fromJson(string)
            else
                null
        }

    }

}