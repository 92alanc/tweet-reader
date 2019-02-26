package com.alancamargo.tweetreader.util

import android.widget.ImageView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader

class AppImageHandler : ImageHandler {

    override fun loadImage(url: String?, target: ImageView) {
        val options = DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .build()

        ImageLoader.getInstance().displayImage(url, target, options)
    }

}