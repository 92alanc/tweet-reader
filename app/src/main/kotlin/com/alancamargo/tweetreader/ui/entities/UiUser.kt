package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiUser(
        val id: Long,
        val name: String,
        val screenName: String,
        val location: String,
        val description: String,
        val followersCount: Int,
        val creationDate: String,
        val profilePictureUrl: String,
        val profileBannerUrl: String
) : Parcelable