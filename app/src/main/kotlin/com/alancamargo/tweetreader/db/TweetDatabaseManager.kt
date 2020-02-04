package com.alancamargo.tweetreader.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.Tweet

@Dao
interface TweetDatabaseManager {

    @Insert
    suspend fun insert(tweet: Tweet)

    @Query("SELECT * FROM Tweet ORDER BY id DESC")
    suspend fun select(): List<Tweet>

    @Query("SELECT COUNT() FROM Tweet WHERE id = :id")
    suspend fun count(id: Long): Int

    @Query("DELETE FROM Tweet")
    suspend fun delete()

}