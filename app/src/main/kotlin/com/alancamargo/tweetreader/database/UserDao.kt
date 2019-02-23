package com.alancamargo.tweetreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alancamargo.tweetreader.model.database.DatabaseUser

@Dao
interface UserDao {

    @Insert
    fun insert(user: DatabaseUser)

    @Query("SELECT * FROM User LIMIT 1")
    fun select(): LiveData<DatabaseUser>

}