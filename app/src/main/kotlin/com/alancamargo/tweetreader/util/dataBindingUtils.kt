package com.alancamargo.tweetreader.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    val imageLoader = ImageLoader.getInstance()
    val options = DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .considerExifParams(true)
        .build()

    imageLoader.displayImage(url, imageView, options)
}

@BindingAdapter("timestamp")
fun setTimestamp(textView: TextView, timestamp: String) {
    val time = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.getDefault())
        .parse(timestamp).time
    textView.text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(time)
}