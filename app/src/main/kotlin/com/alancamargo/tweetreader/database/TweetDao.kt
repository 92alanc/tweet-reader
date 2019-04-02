package com.alancamargo.tweetreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.Tweet

@Dao
interface TweetDao {

    @Insert
    fun insert(tweet: Tweet)

    @Query("SELECT * FROM Tweet")
    fun select(): LiveData<List<Tweet>?>

    @Query("SELECT COUNT() FROM Tweet WHERE id = :id")
    fun count(id: Long): Int

}