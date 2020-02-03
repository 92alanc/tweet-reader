package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.util.ImageHandler
import com.alancamargo.tweetreader.util.LinkClickListener

object OldDependencyInjection {

    lateinit var imageHandler: ImageHandler
        private set

    lateinit var baseUrl: String
        private set

    lateinit var linkClickListener: LinkClickListener
        private set

    fun init(
        imageHandler: ImageHandler,
        baseUrl: String,
        linkClickListener: LinkClickListener
    ) {
        this.imageHandler = imageHandler
        this.baseUrl = baseUrl
        this.linkClickListener = linkClickListener
    }

}