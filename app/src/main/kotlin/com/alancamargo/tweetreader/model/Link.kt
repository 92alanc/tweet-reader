package com.alancamargo.tweetreader.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Tweet::class,
    parentColumns = ["id"],
    childColumns = ["tweetId"],
    onDelete = ForeignKey.CASCADE)])
data class Link(
    var url: String = "",
    var title: String = "",
    var thumbnail: String? = null,
    var summary: String = "",
    var displayUrl: String = "",
    @PrimaryKey var tweetId: Long = 0L
)