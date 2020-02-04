package com.alancamargo.tweetreader.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    @field:Json(name = "screen_name") var screenName: String = "",
    var location: String = "",
    var description: String = "",
    @field:Json(name = "followers_count") var followersCount: Int = 0,
    @field:Json(name = "created_at") var creationDate: String = "",
    @field:Json(name = "profile_image_url_https") var profilePictureUrl: String = "",
    @field:Json(name = "profile_banner_url") var profileBannerUrl: String = ""
) : Parcelable