package com.alancamargo.tweetreader.domain.entities

data class User(
    val id: Long,
    val name: String,
    val screenName: String,
    val location: String,
    val description: String,
    val followersCount: Int,
    val creationDate: String,
    val profilePictureUrl: String,
    val profileBannerUrl: String
)
