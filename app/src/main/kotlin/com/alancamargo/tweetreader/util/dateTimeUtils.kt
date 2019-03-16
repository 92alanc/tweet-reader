package com.alancamargo.tweetreader.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(dateString: String): String {
    val locale = Locale.getDefault()
    val time = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", locale)
        .parse(dateString).time
    return SimpleDateFormat("dd/MM/yy HH:mm", locale).format(time)
}