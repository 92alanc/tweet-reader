package com.alancamargo.tweetreader.services.config

class PaidVersionAdDisplayConfigImpl : PaidVersionAdDisplayConfig {

    private var shouldDisplayAd = false

    override fun setShouldDisplayAd(shouldDisplayAd: Boolean) {
        this.shouldDisplayAd = shouldDisplayAd
    }

    override fun shouldDisplayAd(): Boolean = shouldDisplayAd

}