package com.alancamargo.tweetreader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alancamargo.tweetreader.model.Tweet

@Database(entities = [Tweet::class], version = 6, exportSchema = false)
abstract class TweetDatabaseProvider : RoomDatabase() {

    abstract fun provideDatabase(): TweetDatabaseManager

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