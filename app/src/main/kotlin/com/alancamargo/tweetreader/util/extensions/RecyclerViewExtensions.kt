package com.alancamargo.tweetreader.util.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TOP_POSITION = 0

fun RecyclerView.isFirstItemVisible(): Boolean {
    val layoutManager = this.layoutManager as LinearLayoutManager
    val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
    return firstVisiblePosition == TOP_POSITION
}

fun RecyclerView.scrollToTop() {
    smoothScrollToPosition(TOP_POSITION)
}
