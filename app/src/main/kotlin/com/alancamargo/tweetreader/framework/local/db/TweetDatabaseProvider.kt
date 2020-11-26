package com.alancamargo.tweetreader.framework.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alancamargo.tweetreader.framework.entities.Tweet

@Database(entities = [Tweet::class], version = 6, exportSchema = false)
abstract class TweetDatabaseProvider : RoomDatabase() {

    abstract fun provideDatabase(): TweetDao

    companion object {
        private var instance: TweetDatabaseProvider? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): TweetDatabaseProvider {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    TweetDatabaseProvider::class.java,
                    "twitter_database"
                ).fallbackToDestructiveMigration().build()
            }

            return instance!!
        }
    }

}