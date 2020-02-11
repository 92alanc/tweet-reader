package com.alancamargo.tweetreader.handlers

import android.widget.ImageView

interface ImageHandler {
    suspend fun loadImage(url: String?, target: ImageView)
}