package com.alancamargo.tweetreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alancamargo.tweetreader.model.api.Media
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(Tweet.TweetConverter::class)
data class Tweet(
    @SerializedName("created_at") var creationDate: String = "",
    @PrimaryKey var id: Long = 0,
    @SerializedName("full_text") var text: String = "",
    @SerializedName("user") var author: User = User(),
    @SerializedName("extended_entities") var media: Media? = null,
    @SerializedName("quoted_status") var quotedTweet: Tweet? = null,
    @SerializedName("retweeted_status") var retweet: Tweet? = null,
    @SerializedName("in_reply_to_status_id") var inReplyTo: Long? = null
) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

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
        private val gson = Gson()

        @TypeConverter
        fun userToString(user: User): String = gson.toJson(user)

        @TypeConverter
        fun stringToUser(string: String): User = gson.fromJson(string, User::class.java)

        @TypeConverter
        fun mediaToString(media: Media?): String? {
            return if (media != null)
                gson.toJson(media)
            else
                null
        }

        @TypeConverter
        fun stringToMedia(string: String?): Media? {
            return if (string != null)
                gson.fromJson(string, Media::class.java)
            else
                null
        }

        @TypeConverter
        fun quotedTweetToString(quotedTweet: Tweet?): String? {
            return if (quotedTweet != null)
                gson.toJson(quotedTweet)
            else
                null
        }

        @TypeConverter
        fun stringToQuotedTweet(string: String?): Tweet? {
            return if (string != null)
                gson.fromJson(string, Tweet::class.java)
            else
                null
        }
    }

}