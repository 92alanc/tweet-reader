package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.util.ImageHandler

object DependencyInjection {

    lateinit var imageHandler: ImageHandler
        private set

    lateinit var baseUrl: String
        private set

    fun init(imageHandler: ImageHandler,
             baseUrl: String) {
        this.imageHandler = imageHandler
        this.baseUrl = baseUrl
    }

}