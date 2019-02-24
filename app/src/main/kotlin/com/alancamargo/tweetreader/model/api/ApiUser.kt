package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.model.User
import com.google.gson.annotations.SerializedName

data class ApiUser(
    @SerializedName("id") override var id: Long,
    @SerializedName("name") override var name: String,
    @SerializedName("screen_name") override var screenName: String,
    @SerializedName("location") override var location: String,
    @SerializedName("description") override var description: String,
    @SerializedName("followers_count") override var followersCount: Int,
    @SerializedName("created_at") override var creationDate: String,
    @SerializedName("profile_image_url_https") override var profilePictureUrl: String,
    @SerializedName("profile_banner_url") override var profileBannerUrl: String
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