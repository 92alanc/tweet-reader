package com.alancamargo.tweetreader.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class User(
    open var id: Long = 0,
    open var name: String = "",
    open var screenName: String = "",
    open var location: String = "",
    open var description: String = "",
    open var followersCount: Int = 0,
    open var creationDate: String = "",
    open var profilePictureUrl: String = "",
    open var profileBannerUrl: String = ""
) : Parcelable