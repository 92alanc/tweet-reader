package com.alancamargo.tweetreader.ui.ads

import android.widget.ImageView

interface ImageHandler {
    suspend fun loadImage(url: String?, target: ImageView)
}