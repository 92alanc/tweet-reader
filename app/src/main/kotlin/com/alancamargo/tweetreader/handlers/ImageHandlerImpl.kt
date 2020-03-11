package com.alancamargo.tweetreader.handlers

import android.widget.ImageView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader

class ImageHandlerImpl : ImageHandler {

    override suspend fun loadImage(url: String?, target: ImageView) {
        val options = DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .build()

        ImageLoader.getInstance()
            .displayImage(url?.replace("_normal", ""), target, options)
    }

}