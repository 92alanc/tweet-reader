package com.alancamargo.tweetreader.ui.tools

import android.view.View
import androidx.annotation.StringRes

interface AdLoader {

    fun loadBannerAds(target: View, @StringRes adId: Int)

    fun destroyBannerAds(target: View)

    fun loadNativeAds(target: View)

}