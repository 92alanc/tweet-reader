package com.alancamargo.tweetreader.framework.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.framework.entities.TweetResponse

@Dao
interface TweetDao {

    @Insert
    suspend fun insert(tweet: TweetResponse)

    @Query("SELECT * FROM TweetResponse ORDER BY id DESC")
    suspend fun select(): List<TweetResponse>

    @Query("SELECT COUNT() FROM TweetResponse WHERE id = :id")
    suspend fun count(id: Long): Int

    @Query("DELETE FROM TweetResponse")
    suspend fun delete()

}