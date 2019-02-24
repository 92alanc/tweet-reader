package com.alancamargo.tweetreader.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alancamargo.tweetreader.model.User

@Entity(tableName = "User")
class DatabaseUser : User() {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    override var id: Long = 0

    @ColumnInfo(name = "user_name")
    override var name: String = ""

    @ColumnInfo(name = "user_screen_name")
    override var screenName: String = ""

    @ColumnInfo(name = "user_location")
    override var location: String = ""

    @ColumnInfo(name = "user_description")
    override var description: String = ""

    @ColumnInfo(name = "user_followers_count")
    override var followersCount: Int = 0

    @ColumnInfo(name = "user_creation_date")
    override var creationDate: String = ""

    @ColumnInfo(name = "user_profile_picture_url")
    override var profilePictureUrl: String = ""

    @ColumnInfo(name = "user_profile_banner_url")
    override var profileBannerUrl: String = ""

}