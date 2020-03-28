package com.alancamargo.tweetreader.model.api

import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

data class ExtendedTweet(@field:Json(name = "full_text") val text: String) {

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val extendedTweetAdapter = moshi.adapter(ExtendedTweet::class.java)

        @TypeConverter
        fun extendedTweetToString(extendedTweet: ExtendedTweet?): String? {
            return if (extendedTweet != null)
                extendedTweetAdapter.toJson(extendedTweet)
            else
                null
        }

        @TypeConverter
        fun stringToExtendedTweet(string: String?): ExtendedTweet? {
            return if (string != null)
                extendedTweetAdapter.fromJson(string)
            else
                null
        }

    }

}