package com.alancamargo.tweetreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.alancamargo.tweetreader.di.DependencyInjection
import com.github.chrisbanes.photoview.PhotoView

class PhotoDetailsViewModel(application: Application) : AndroidViewModel(application) {

    fun loadPhoto(photo: String, photoView: PhotoView) {
        DependencyInjection.imageHandler.loadImage(photo, photoView)
    }

}