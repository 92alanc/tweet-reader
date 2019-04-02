package com.alancamargo.tweetreader.repository

import com.alancamargo.tweetreader.model.Link

interface LinkCallback {
    fun onStartLoading()
    fun onPreviewReady(link: Link)
}