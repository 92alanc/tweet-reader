package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.ui.ads.AdLoader
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.smaato.sdk.nativead.NativeAd
import com.smaato.sdk.nativead.NativeAdError
import com.smaato.sdk.nativead.NativeAdRenderer

class AdViewHolder(
    itemView: View,
    private val adLoader: AdLoader
) : RecyclerView.ViewHolder(itemView), NativeAd.Listener {

    private lateinit var txtError: MaterialTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var groupNativeAd: Group

    fun loadAd() {
        bindViews()
        showProgressBar()
        txtError.visibility = View.GONE

        adLoader.loadNativeAds(itemView, R.string.ads_native, listener = this)
    }

    override fun onAdLoaded(ad: NativeAd, renderer: NativeAdRenderer) = with(renderer) {
        hideProgressBar()
        val callToAction = findView<MaterialButton>(R.id.ad_call_to_action)

        registerForImpression(itemView)
        registerForClicks(callToAction)

        findView<ImageView>(R.id.ad_app_icon).setImageDrawable(assets.icon()?.drawable())
        findView<ImageView>(R.id.ad_media).setImageDrawable(assets.images().first().drawable())

        findView<MaterialTextView>(R.id.ad_headline).text = assets.title()
        findView<MaterialTextView>(R.id.ad_body).text = assets.text()

        callToAction.text = assets.cta()
    }

    override fun onAdFailedToLoad(ad: NativeAd, error: NativeAdError) {
        progressBar.visibility = View.GONE
        txtError.visibility = View.VISIBLE
    }

    override fun onAdImpressed(ad: NativeAd) { }

    override fun onAdClicked(ad: NativeAd) { }

    override fun onTtlExpired(ad: NativeAd) { }

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

    private fun <V: View> findView(@IdRes viewId: Int) = itemView.findViewById<V>(viewId)

}