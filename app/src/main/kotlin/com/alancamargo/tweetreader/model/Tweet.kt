package com.alancamargo.tweetreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alancamargo.tweetreader.api.MEDIA_PHOTO
import com.alancamargo.tweetreader.api.MEDIA_VIDEO
import com.alancamargo.tweetreader.model.api.Media
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi

@Entity
@TypeConverters(Tweet.TweetConverter::class)
data class Tweet(
    @field:Json(name = "created_at") var creationDate: String = "",
    @PrimaryKey var id: Long = 0,
    @field:Json(name = "full_text") var text: String = "",
    @field:Json(name = "user") var author: User = User(),
    @field:Json(name = "extended_entities") var media: Media? = null,
    @field:Json(name = "quoted_status") var quotedTweet: Tweet? = null,
    @field:Json(name = "retweeted_status") var retweet: Tweet? = null,
    @field:Json(name = "in_reply_to_status_id") var inReplyTo: Long? = null,
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

    class TweetConverter {
        private val moshi = Moshi.Builder().build()
        private val tweetAdapter = moshi.adapter(Tweet::class.java)
        private val userAdapter = moshi.adapter(User::class.java)
        private val mediaAdapter = moshi.adapter(Media::class.java)

        @TypeConverter
        fun userToString(user: User): String = userAdapter.toJson(user)

        @TypeConverter
        fun stringToUser(string: String): User? = userAdapter.fromJson(string)

        @TypeConverter
        fun mediaToString(media: Media?): String? {
            return if (media != null)
                mediaAdapter.toJson(media)
            else
                null
        }

        @TypeConverter
        fun stringToMedia(string: String?): Media? {
            return if (string != null)
                mediaAdapter.fromJson(string)
            else
                null
        }

        @TypeConverter
        fun quotedTweetToString(quotedTweet: Tweet?): String? {
            return if (quotedTweet != null)
                tweetAdapter.toJson(quotedTweet)
            else
                null
        }

        @TypeConverter
        fun stringToQuotedTweet(string: String?): Tweet? {
            return if (string != null)
                tweetAdapter.fromJson(string)
            else
                null
        }
    }

}