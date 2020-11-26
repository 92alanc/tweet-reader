package com.alancamargo.tweetreader.framework.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
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
) : Parcelable {

    class Converter {

        private val moshi = Moshi.Builder().build()
        private val userAdapter = moshi.adapter(User::class.java)

        @TypeConverter
        fun userToString(user: User): String = userAdapter.toJson(user)

        @TypeConverter
        fun stringToUser(string: String): User? = userAdapter.fromJson(string)

    }

}