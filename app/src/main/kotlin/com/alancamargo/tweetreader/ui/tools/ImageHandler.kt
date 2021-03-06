package com.alancamargo.tweetreader.ui.tools

import android.widget.ImageView

interface ImageHandler {
    suspend fun loadImage(url: String?, target: ImageView)
}