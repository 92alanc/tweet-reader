package com.alancamargo.tweetreader.adapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extractLinkFrom
import com.crashlytics.android.Crashlytics
import io.github.ponnamkarthik.richlinkpreview.MetaData
import io.github.ponnamkarthik.richlinkpreview.ResponseListener
import io.github.ponnamkarthik.richlinkpreview.RichPreview

class LinkTweetViewHolder(itemView: View) : TweetViewHolder(itemView), ResponseListener {

    private val imgThumbnail = itemView.findViewById<ImageView>(R.id.img_link_thumbnail)
    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_link_title)
    private val txtLink = itemView.findViewById<TextView>(R.id.txt_link)
    private val txtContent = itemView.findViewById<TextView>(R.id.txt_link_text)
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    private val previewCard = itemView.findViewById<CardView>(R.id.card_link_preview)

    private var link: String? = null

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        loadLinkPreview(tweet)
    }

    override fun onData(metaData: MetaData?) {
        progressBar.visibility = GONE
        previewCard.visibility = VISIBLE

        metaData?.let { linkData ->
            txtTitle.text = linkData.title
            txtLink.text = linkData.url
            txtContent.text = linkData.description
            DependencyInjection.imageHandler.loadImage(linkData.imageurl, imgThumbnail)
        }
    }

    override fun onError(e: Exception?) {
        progressBar.visibility = GONE

        Crashlytics.log("Error loading $link")
        Crashlytics.logException(e)
    }

    private fun loadLinkPreview(tweet: Tweet) {
        link = extractLinkFrom(tweet.text)
        RichPreview(this).getPreview(link)

        previewCard.visibility = GONE
        progressBar.visibility = VISIBLE
    }

}