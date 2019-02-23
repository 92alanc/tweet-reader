package com.alancamargo.tweetreader.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.google.gson.Gson

@Entity
data class DatabaseTweet(
    override val creationDate: String,
    @PrimaryKey(autoGenerate = false) override val id: Long,
    override val text: String,
    @TypeConverters(UserTypeConverter::class) override val author: User
) : Tweet(creationDate, id, text, author) {

    class UserTypeConverter {

        private val gson = Gson()

        @TypeConverter
        fun userToString(user: User) = gson.toJson(user)

        @TypeConverter
        fun stringToUser(string: String) = gson.fromJson(string, User::class.java)

    }

}