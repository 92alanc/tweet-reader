package com.alancamargo.tweetreader.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alancamargo.tweetreader.di.DependencyInjection
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    DependencyInjection.imageHandler.loadImage(url, imageView)
}

@BindingAdapter("timestamp")
fun setTimestamp(textView: TextView, timestamp: String) {
    val time = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.getDefault())
        .parse(timestamp).time
    textView.text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(time)
}