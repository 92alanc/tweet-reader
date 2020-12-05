package com.alancamargo.tweetreader.framework.entities

import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

data class MediaResponse(@field:Json(name = "media") val contents: List<MediaContentResponse>?) {

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