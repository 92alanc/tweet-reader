package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiVideoInfo(val aspectRatio: List<Int>, val variants: List<UiVideoVariant>) : Parcelable