package com.alancamargo.tweetreader.ui.ads

import android.view.View
import com.smaato.sdk.banner.widget.BannerView
import com.smaato.sdk.nativead.NativeAd
import com.smaato.sdk.nativead.NativeAdRequest
import com.smaato.sdk.sys.Lifecycling

class AdLoaderImpl : AdLoader {

    override fun loadBannerAds(target: View, adId: Int) {
        with(target as BannerView) {
            loadAd(context.getString(adId), null)
        }
    }

    override fun destroyBannerAds(target: View) {
        (target as BannerView).destroy()
    }

    override fun loadNativeAds(target: View, adId: Int, listener: NativeAd.Listener) {
        val context = target.context
        val request = NativeAdRequest.builder()
                .adSpaceId(context.getString(adId))
                .shouldReturnUrlsForImageAssets(false)
                .build()

        NativeAd.loadAd(Lifecycling.of(target), request, listener)
    }

}