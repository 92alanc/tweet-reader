package com.alancamargo.tweetreader.util

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

fun AdView.loadBannerAds() {
    val adRequest = AdRequest.Builder().build()
    loadAd(adRequest)
}

fun UnifiedNativeAdView.loadNativeAds(ad: UnifiedNativeAd) {
    bindViews()
    fillViews(ad)
    setNativeAd(ad)
}

private fun UnifiedNativeAdView.bindViews() {
    mediaView = findViewById(R.id.ad_media)
    headlineView = findViewById(R.id.ad_headline)
    advertiserView = findViewById(R.id.ad_advertiser)
    bodyView = findViewById(R.id.ad_body)
    callToActionView = findViewById(R.id.ad_call_to_action)
    iconView = findViewById(R.id.ad_app_icon)
    priceView = findViewById(R.id.ad_price)
    storeView = findViewById(R.id.ad_store)
}

private fun UnifiedNativeAdView.fillViews(ad: UnifiedNativeAd) {
    (headlineView as TextView).text = ad.headline
    mediaView.setMediaContent(ad.mediaContent)
    fillBody(ad)
    fillCallToAction(ad)
    fillIcon(ad)
    fillPrice(ad)
    fillStore(ad)
    fillAdvertiser(ad)
}

private fun UnifiedNativeAdView.fillBody(ad: UnifiedNativeAd) {
    if (ad.body == null) {
        bodyView.visibility = View.GONE
    } else {
        bodyView.visibility = View.VISIBLE
        (bodyView as TextView).text = ad.body
    }
}

private fun UnifiedNativeAdView.fillCallToAction(ad: UnifiedNativeAd) {
    if (ad.callToAction == null) {
        callToActionView.visibility = View.GONE
    } else {
        callToActionView.visibility = View.VISIBLE
        (callToActionView as Button).text = ad.callToAction
    }
}

private fun UnifiedNativeAdView.fillIcon(ad: UnifiedNativeAd) {
    if (ad.icon == null) {
        iconView.visibility = View.GONE
    } else {
        (iconView as ImageView).setImageDrawable(
            ad.icon.drawable
        )
        iconView.visibility = View.VISIBLE
    }
}

private fun UnifiedNativeAdView.fillPrice(ad: UnifiedNativeAd) {
    if (ad.price == null) {
        priceView.visibility = View.GONE
    } else {
        priceView.visibility = View.VISIBLE
        (priceView as TextView).text = ad.price
    }
}

private fun UnifiedNativeAdView.fillStore(ad: UnifiedNativeAd) {
    if (ad.store == null) {
        storeView.visibility = View.GONE
    } else {
        storeView.visibility = View.VISIBLE
        (storeView as TextView).text = ad.store
    }
}

private fun UnifiedNativeAdView.fillAdvertiser(ad: UnifiedNativeAd) {
    if (ad.advertiser == null) {
        advertiserView.visibility = View.GONE
    } else {
        (advertiserView as TextView).text = ad.advertiser
        advertiserView.visibility = View.VISIBLE
    }
}
