package com.alancamargo.tweetreader.viewmodel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tweetreader.handlers.ImageHandler
import kotlinx.coroutines.launch

class ProfileViewModel(private val imageHandler: ImageHandler) : ViewModel() {

    fun loadPhoto(photoUrl: String, imageView: ImageView) {
        viewModelScope.launch {
            imageHandler.loadImage(photoUrl, imageView)
        }
    }

}