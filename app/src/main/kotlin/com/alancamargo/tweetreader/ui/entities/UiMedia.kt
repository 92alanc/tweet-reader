package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import android.util.Size
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMedia(val contents: List<UiMediaContent>?) : Parcelable {

    fun getVideoAspectRatio(): Size? {
        val aspectRatio = contents?.first()
                ?.videoInfo
                ?.aspectRatio

        return if (aspectRatio != null)
            Size(aspectRatio.first(), aspectRatio.last())
        else
            null
    }

}