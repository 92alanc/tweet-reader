package com.alancamargo.tweetreader.framework.entities

import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

data class ExtendedTweetResponse(@field:Json(name = "full_text") val text: String) {

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val extendedTweetAdapter = moshi.adapter(ExtendedTweetResponse::class.java)

        @TypeConverter
        fun extendedTweetToString(extendedTweet: ExtendedTweetResponse?): String? {
            return if (extendedTweet != null)
                extendedTweetAdapter.toJson(extendedTweet)
            else
                null
        }

        @TypeConverter
        fun stringToExtendedTweet(string: String?): ExtendedTweetResponse? {
            return if (string != null)
                extendedTweetAdapter.fromJson(string)
            else
                null
        }

    }

}