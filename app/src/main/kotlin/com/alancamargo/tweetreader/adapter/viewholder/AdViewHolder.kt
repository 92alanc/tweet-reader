package com.alancamargo.tweetreader.adapter.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.util.bindView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mMediaView by bindView<MediaView>(R.id.media_view)
    private val headline by bindView<TextView>(R.id.txt_ad_headline)
    private val content by bindView<TextView>(R.id.txt_ad_content)

    fun start() {
        val context = itemView.context
        val adUnitId = context.getString(R.string.ad_unit_id_native)
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forUnifiedNativeAd { ad : UnifiedNativeAd ->
                val inflater = LayoutInflater.from(context)
                val adView = inflater.inflate(
                    R.layout.item_ad, itemView as ViewGroup, false
                ) as UnifiedNativeAdView

                with(adView) {
                    mediaView = mMediaView
                    headlineView = headline
                    bodyView = content

                    setNativeAd(ad)
                    // FIXME
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    Log.e(javaClass.simpleName, "Failed to load ads. Code: $errorCode")
                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

}