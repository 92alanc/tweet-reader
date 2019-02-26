package com.alancamargo.tweetreader.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    @SerializedName("screen_name") var screenName: String = "",
    var location: String = "",
    var description: String = "",
    @SerializedName("followers_count") var followersCount: Int = 0,
    @SerializedName("created_at") var creationDate: String = "",
    @SerializedName("profile_image_url_https") var profilePictureUrl: String = "",
    @SerializedName("profile_banner_url") var profileBannerUrl: String = ""
) : Parcelable