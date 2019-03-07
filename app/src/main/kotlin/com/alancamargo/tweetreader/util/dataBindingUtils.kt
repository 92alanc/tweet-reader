package com.alancamargo.tweetreader.util

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.di.DependencyInjection
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

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

@BindingAdapter("memberSince")
fun setMemberSince(textView: TextView, date: String) {
    val time = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.getDefault())
        .parse(date).time
    val context = textView.context
    val formattedDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(time)
    textView.text = context.getString(R.string.member_since_format, formattedDate)
}

@BindingAdapter("tweetText")
fun setTweetText(textView: TextView, rawText: String) {
    val words = rawText.getWords()
    val formattedText = SpannableString(rawText)

    for (word in words) {
        val hashtag = Pattern.compile("#(.)+").matcher(word)
        val account = Pattern.compile("@([a-z]|[A-Z]|[0-9]|_|\\.|-)+").matcher(word)
        val url = Pattern.compile("((http|https)(://))([a-z]|[A-Z]|[0-9]|[.]|-|/|&|\\?|#|_|=)+")
            .matcher(word)

        if (hashtag.matches() || account.matches() || url.matches()) {
            formattedText.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(textView.context, R.color.light_blue)),
                rawText.indexOf(word),
                rawText.indexOf(word) + word.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    textView.text = formattedText.trim()
}