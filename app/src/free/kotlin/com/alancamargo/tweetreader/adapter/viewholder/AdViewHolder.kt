package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.util.extensions.loadNativeAds
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.free.item_ad.*

class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View? = itemView

    fun loadAd() {
        showProgressBar()
        txt_error.visibility = View.GONE
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
                    progress_bar.visibility = View.GONE
                    txt_error.visibility = View.VISIBLE
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun showProgressBar() {
        group_native_ad.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        group_native_ad.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

}