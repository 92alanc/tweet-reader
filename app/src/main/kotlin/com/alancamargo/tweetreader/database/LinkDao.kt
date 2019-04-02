package com.alancamargo.tweetreader.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.Link

@Dao
interface LinkDao {

    @Insert
    fun insert(link: Link)

    @Query("SELECT * FROM Link WHERE tweetId = :tweetId LIMIT 1")
    fun select(tweetId: Long): Link

    @Query("SELECT COUNT() FROM Link WHERE tweetId = :tweetId")
    fun count(tweetId: Long): Int

}