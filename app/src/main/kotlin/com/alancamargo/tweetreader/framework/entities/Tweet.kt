package com.alancamargo.tweetreader.framework.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alancamargo.tweetreader.data.remote.MEDIA_PHOTO
import com.alancamargo.tweetreader.data.remote.MEDIA_VIDEO
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

@Entity
@TypeConverters(
    Tweet.Converter::class,
    User.Converter::class,
    Media.Converter::class,
    ExtendedTweet.Converter::class
)
data class Tweet(
    @field:Json(name = "created_at") var creationDate: String = "",
    @PrimaryKey var id: Long = 0,
    @field:Json(name = "full_text") var fullText: String = "",
    @field:Json(name = "text") var text: String = "",
    @field:Json(name = "user") var author: User = User(),
    @field:Json(name = "extended_entities") var media: Media? = null,
    @field:Json(name = "quoted_status") var quotedTweet: Tweet? = null,
    @field:Json(name = "retweeted_status") var retweet: Tweet? = null,
    @field:Json(name = "in_reply_to_status_id") var inReplyTo: Long? = null,
    @field:Json(name = "extended_tweet") var extendedTweet: ExtendedTweet? = null,
    @Transient var repliedTweet: Tweet? = null
) {

    fun isQuoting() = quotedTweet != null

    fun isRetweet() = retweet != null

    fun isReply() = inReplyTo != null

    fun containsPhoto() = media?.contents?.any { it.type == MEDIA_PHOTO } ?: false

    fun containsVideo() = media?.contents?.any { it.type == MEDIA_VIDEO } ?: false

    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is Tweet)
            false
        else
            id == other.id
    }

    override fun hashCode(): Int {
        return id.toInt()
    }

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val tweetAdapter = moshi.adapter(Tweet::class.java)

        @TypeConverter
        fun tweetToString(tweet: Tweet?): String? {
            return if (tweet != null)
                tweetAdapter.toJson(tweet)
            else
                null
        }

        @TypeConverter
        fun stringToTweet(string: String?): Tweet? {
            return if (string != null)
                tweetAdapter.fromJson(string)
            else
                null
        }

    }

}