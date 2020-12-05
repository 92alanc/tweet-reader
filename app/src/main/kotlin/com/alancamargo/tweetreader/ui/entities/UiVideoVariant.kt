package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiVideoVariant(val contentType: String, val url: String) : Parcelable