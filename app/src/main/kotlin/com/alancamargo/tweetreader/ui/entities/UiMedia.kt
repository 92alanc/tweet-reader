package com.alancamargo.tweetreader.ui.entities

import android.os.Parcelable
import android.util.Size
import com.alancamargo.tweetreader.data.remote.CONTENT_TYPE_MP4
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiMedia(val contents: List<UiMediaContent>?) : Parcelable {

    fun getPhotoUrls() = contents?.map { it.photoUrl }

    fun getVideoUrl(): String? {
        return contents?.first()
                ?.videoInfo
                ?.variants
                ?.first { variant ->
                    variant.contentType == CONTENT_TYPE_MP4
                }
                ?.url
    }

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