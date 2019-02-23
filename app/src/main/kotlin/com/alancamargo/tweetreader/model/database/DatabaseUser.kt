package com.alancamargo.tweetreader.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alancamargo.tweetreader.model.User

@Entity
data class DatabaseUser(
    @PrimaryKey(autoGenerate = false) override val id: Long,
    override val name: String,
    override val screenName: String,
    override val location: String,
    override val description: String,
    override val followersCount: Int,
    override val creationDate: String,
    override val profilePictureUrl: String,
    override val profileBannerUrl: String
) : User(
    id,
    name,
    screenName,
    location,
    description,
    followersCount,
    creationDate,
    profilePictureUrl,
    profileBannerUrl
)