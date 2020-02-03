package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.util.LinkClickListener

object OldDependencyInjection {

    lateinit var linkClickListener: LinkClickListener
        private set

    fun init(linkClickListener: LinkClickListener) {
        this.linkClickListener = linkClickListener
    }

}