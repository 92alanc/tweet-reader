package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.tools.AdLoader
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.extensions.LayoutContainer

class AdViewHolder(
    itemView: View,
    private val adLoader: AdLoader
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View = itemView

    private lateinit var txtError: MaterialTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var groupNativeAd: Group

    fun loadAd() {
        bindViews()
        showProgressBar()
        txtError.visibility = View.GONE
        val context = itemView.context
        /*val adView = itemView as UnifiedNativeAdView
        val adUnitId = context.getString(R.string.ad_unit_id_native)
        val adLoader = UselessAdLoader.Builder(context, adUnitId)
            .forUnifiedNativeAd { ad ->
                hideProgressBar()
                adView.loadNativeAds(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    progressBar.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())*/
    }

    private fun bindViews() = with(itemView) {
        txtError = findViewById(R.id.txt_error)
        progressBar = findViewById(R.id.progress_bar)
        groupNativeAd = findViewById(R.id.group_native_ad)
    }

    private fun showProgressBar() {
        groupNativeAd.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        groupNativeAd.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

}