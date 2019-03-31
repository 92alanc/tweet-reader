package com.alancamargo.tweetreader.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    private var isLoading = false

    abstract fun onLoadMore()

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (!isLoading && !recyclerView.canScrollVertically(RecyclerView.LAYOUT_DIRECTION_RTL)) {
            isLoading = true
            onLoadMore()
        } else {
            isLoading = false
        }
    }

}