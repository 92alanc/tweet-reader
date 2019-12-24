package com.alancamargo.tweetreader.adapter.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.util.loadNativeAds
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAdView

class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun loadAd() {
        val context = itemView.context
        val adView = itemView as UnifiedNativeAdView
        val adUnitId = context.getString(R.string.ad_unit_id_native)
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forUnifiedNativeAd { ad ->
                adView.loadNativeAds(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    Log.e(javaClass.simpleName, "Failed to load ad. Error code: $errorCode")
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

}