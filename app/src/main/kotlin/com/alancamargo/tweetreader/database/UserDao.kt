package com.alancamargo.tweetreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    fun select(): LiveData<User>

    @Query("SELECT COUNT() FROM User WHERE id = :id")
    fun count(id: Long): Int

}