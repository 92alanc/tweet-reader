package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.util.loadNativeAds
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAdView

class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val adContent: Group = itemView.findViewById(R.id.group_native_ad)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
    private val errorText: TextView = itemView.findViewById(R.id.txt_error)

    fun loadAd() {
        showProgressBar()
        errorText.visibility = View.GONE
        val context = itemView.context
        val adView = itemView as UnifiedNativeAdView
        val adUnitId = context.getString(R.string.ad_unit_id_native)
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forUnifiedNativeAd { ad ->
                hideProgressBar()
                adView.loadNativeAds(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    Crashlytics.log("Failed to load ad. Error code: $errorCode")
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun showProgressBar() {
        adContent.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        adContent.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

}