package com.alancamargo.tweetreader.ui.tools

import android.view.View
import com.smaato.sdk.banner.widget.BannerView

class AdLoaderImpl : AdLoader {

    override fun loadBannerAds(target: View, adId: Int) {
        with(target as BannerView) {
            loadAd(context.getString(adId), null)
        }
    }

    override fun destroyBannerAds(target: View) {
        (target as BannerView).destroy()
    }

    override fun loadNativeAds(target: View) {
        // Do nothing
    }

}