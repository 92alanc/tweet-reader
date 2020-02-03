package com.alancamargo.tweetreader.viewmodel

import androidx.lifecycle.ViewModel
import com.alancamargo.tweetreader.util.ImageHandler
import com.github.chrisbanes.photoview.PhotoView

class PhotoDetailsViewModel(private val imageHandler: ImageHandler) : ViewModel() {

    fun loadPhoto(photo: String, photoView: PhotoView) {
        imageHandler.loadImage(photo, photoView)
    }

}