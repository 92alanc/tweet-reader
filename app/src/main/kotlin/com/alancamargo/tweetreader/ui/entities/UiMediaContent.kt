package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMediaContent(
        val type: String,
        val photoUrl: String,
        val videoInfo: UiVideoInfo?
) : Parcelable