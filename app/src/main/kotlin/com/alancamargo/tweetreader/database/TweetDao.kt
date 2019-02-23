package com.alancamargo.tweetreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.database.DatabaseTweet

@Dao
interface TweetDao {

    @Insert
    fun insert(tweet: DatabaseTweet)

    @Query("SELECT * FROM Tweet")
    fun select(): LiveData<List<DatabaseTweet>>

}