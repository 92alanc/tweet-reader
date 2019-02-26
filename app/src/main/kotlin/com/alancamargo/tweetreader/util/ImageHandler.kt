package com.alancamargo.tweetreader.util

import android.widget.ImageView

interface ImageHandler {
    fun loadImage(url: String?, target: ImageView)
}