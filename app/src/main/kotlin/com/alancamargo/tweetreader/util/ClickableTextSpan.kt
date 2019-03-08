package com.alancamargo.tweetreader.util

import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class ClickableTextSpan(private val textColour: Int) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = false
        ds.color = textColour
    }

}