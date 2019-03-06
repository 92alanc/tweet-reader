package com.alancamargo.tweetreader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alancamargo.tweetreader.model.Tweet

@Database(entities = [Tweet::class], version = 1, exportSchema = false)
abstract class TweetDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao

    companion object {
        private var instance: TweetDatabase? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): TweetDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, TweetDatabase::class.java, "tweet_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }
    }

}