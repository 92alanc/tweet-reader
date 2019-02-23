package com.alancamargo.tweetreader.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alancamargo.tweetreader.model.Tweet

@Entity
data class DatabaseTweet(
    override val creationDate: String,
    @PrimaryKey(autoGenerate = false) override val id: Long,
    override val text: String
) : Tweet(creationDate, id, text)