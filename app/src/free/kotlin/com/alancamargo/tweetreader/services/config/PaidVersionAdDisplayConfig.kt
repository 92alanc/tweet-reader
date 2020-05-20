package com.alancamargo.tweetreader.services.config

interface PaidVersionAdDisplayConfig {

    fun setShouldDisplayAd(shouldDisplayAd: Boolean)

    fun shouldDisplayAd(): Boolean

}