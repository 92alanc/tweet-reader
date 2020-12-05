package com.alancamargo.tweetreader.framework.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

@Entity
@TypeConverters(
    TweetResponse.Converter::class,
    UserResponse.Converter::class,
    MediaResponse.Converter::class,
    ExtendedTweetResponse.Converter::class
)
data class TweetResponse(
    @field:Json(name = "created_at") var creationDate: String = "",
    @PrimaryKey var id: Long = 0,
    @field:Json(name = "full_text") var fullText: String = "",
    @field:Json(name = "text") var text: String = "",
    @field:Json(name = "user") var author: UserResponse = UserResponse(),
    @field:Json(name = "extended_entities") var media: MediaResponse? = null,
    @field:Json(name = "quoted_status") var quotedTweet: TweetResponse? = null,
    @field:Json(name = "retweeted_status") var retweet: TweetResponse? = null,
    @field:Json(name = "in_reply_to_status_id") var inReplyTo: Long? = null,
    @field:Json(name = "extended_tweet") var extendedTweet: ExtendedTweetResponse? = null,
    @Transient var repliedTweet: TweetResponse? = null
) {

    fun isReply() = inReplyTo != null

    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is TweetResponse)
            false
        else
            id == other.id
    }

    override fun hashCode(): Int {
        return id.toInt()
    }

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val tweetAdapter = moshi.adapter(TweetResponse::class.java)

        @TypeConverter
        fun tweetToString(tweet: TweetResponse?): String? {
            return if (tweet != null)
                tweetAdapter.toJson(tweet)
            else
                null
        }

        @TypeConverter
        fun stringToTweet(string: String?): TweetResponse? {
            return if (string != null)
                tweetAdapter.fromJson(string)
            else
                null
        }

    }

}