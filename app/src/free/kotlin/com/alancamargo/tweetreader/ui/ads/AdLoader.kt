package com.alancamargo.tweetreader.ui.ads

import android.view.View
import androidx.annotation.StringRes
import com.smaato.sdk.nativead.NativeAd

interface AdLoader {

    fun loadBannerAds(target: View, @StringRes adId: Int)

    fun destroyBannerAds(target: View)

    fun loadNativeAds(target: View, @StringRes adId: Int, listener: NativeAd.Listener)

}