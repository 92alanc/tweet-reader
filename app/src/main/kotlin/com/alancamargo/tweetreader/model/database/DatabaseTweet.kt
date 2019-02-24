package com.alancamargo.tweetreader.model.database

import androidx.room.*
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.model.User
import com.google.gson.Gson

@Entity(tableName = "Tweet")
@TypeConverters(DatabaseTweet.UserTypeConverter::class)
class DatabaseTweet : Tweet() {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "tweet_id")
    override var id: Long = 0

    @ColumnInfo(name = "tweet_creation_date")
    override var creationDate: String = ""

    @ColumnInfo(name = "tweet_text")
    override var text: String = ""

    @ColumnInfo(name = "tweet_author")
    override var author: User = User()

    @Suppress("unused")
    class UserTypeConverter {

        private val gson = Gson()

        @TypeConverter
        fun userToString(user: User) = gson.toJson(user)

        @TypeConverter
        fun stringToUser(string: String) = gson.fromJson(string, User::class.java)

    }

}