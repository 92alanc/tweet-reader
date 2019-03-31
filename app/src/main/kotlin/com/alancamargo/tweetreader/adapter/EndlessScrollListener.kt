package com.alancamargo.tweetreader.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var previousTotalItemCount = 0
    private var isLoading = true

    abstract fun onLoadMore()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if (totalItemCount < previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0)
                isLoading = true
        }

        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && lastVisibleItemPosition == (totalItemCount - 1)) {
            onLoadMore()
            isLoading = true
        }
    }

}