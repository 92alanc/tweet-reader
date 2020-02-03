package com.alancamargo.tweetreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alancamargo.tweetreader.util.ImageHandler

class PhotoDetailsViewModelFactory(
    private val imageHandler: ImageHandler
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoDetailsViewModel::class.java))
            return PhotoDetailsViewModel(imageHandler) as T
        else throw IllegalStateException("ViewModel not found")
    }

}