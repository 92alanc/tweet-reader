package com.alancamargo.tweetreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(Tweet.UserConverter::class)
open class Tweet(
    @SerializedName("created_at") open var creationDate: String = "",
    @PrimaryKey open var id: Long = 0,
    @SerializedName("full_text") open var text: String = "",
    @SerializedName("user") open var author: User = User()
) {

    class UserConverter {
        private val gson = Gson()

        @TypeConverter
        fun userToString(user: User): String = gson.toJson(user)

        @TypeConverter
        fun stringToUser(string: String): User = gson.fromJson<User>(string, User::class.java)
    }

}