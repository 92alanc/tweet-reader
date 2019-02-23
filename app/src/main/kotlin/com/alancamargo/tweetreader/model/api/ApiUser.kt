package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.model.User
import com.google.gson.annotations.SerializedName

data class ApiUser(
    @SerializedName("id") override val id: Long,
    @SerializedName("name") override val name: String,
    @SerializedName("screen_name") override val screenName: String,
    @SerializedName("location") override val location: String,
    @SerializedName("description") override val description: String,
    @SerializedName("followers_count") override val followersCount: Int,
    @SerializedName("created_at") override val creationDate: String,
    @SerializedName("profile_image_url_https") override val profilePictureUrl: String,
    @SerializedName("profile_banner_url") override val profileBannerUrl: String
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