package com.alancamargo.tweetreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.Tweet

@Dao
interface TweetDao {

    @Insert
    suspend fun insert(tweet: Tweet)

    @Query("SELECT * FROM Tweet ORDER BY id DESC")
    suspend fun select(): List<Tweet>?

    @Query("SELECT * FROM Tweet WHERE id = :id")
    suspend fun select(id: Long): Tweet?

    @Query("SELECT COUNT() FROM Tweet WHERE id = :id")
    suspend fun count(id: Long): Int

}