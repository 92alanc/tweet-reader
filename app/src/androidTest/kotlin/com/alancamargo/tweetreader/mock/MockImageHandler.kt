package com.alancamargo.tweetreader.mock

import android.widget.ImageView
import com.alancamargo.tweetreader.util.ImageHandler

object MockImageHandler : ImageHandler {

    override fun loadImage(url: String?, target: ImageView) {
        target.setImageResource(android.R.drawable.ic_delete)
    }

}