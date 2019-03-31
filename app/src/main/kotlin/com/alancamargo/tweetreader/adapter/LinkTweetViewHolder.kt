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
import com.leocardz.link.preview.library.LinkPreviewCallback
import com.leocardz.link.preview.library.SourceContent
import com.leocardz.link.preview.library.TextCrawler

class LinkTweetViewHolder(itemView: View) : TweetViewHolder(itemView), LinkPreviewCallback {

    private val imgThumbnail = itemView.findViewById<ImageView>(R.id.img_link_thumbnail)
    private val txtTitle = itemView.findViewById<TextView>(R.id.txt_link_title)
    private val txtLink = itemView.findViewById<TextView>(R.id.txt_link)
    private val txtContent = itemView.findViewById<TextView>(R.id.txt_link_text)
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
    private val previewCard = itemView.findViewById<CardView>(R.id.card_link_preview)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        loadLinkPreview(tweet)
    }

    override fun onPre() {
        progressBar.visibility = VISIBLE
    }

    override fun onPos(sourceContent: SourceContent, b: Boolean) {
        progressBar.visibility = GONE
        if (sourceContent.images.isNotEmpty())
            DependencyInjection.imageHandler.loadImage(sourceContent.images.first(), imgThumbnail)
        txtTitle.text = sourceContent.title
        txtLink.text = sourceContent.url
        txtContent.text = sourceContent.description
        previewCard.setOnClickListener {
            // TODO: open link
        }
    }

    private fun loadLinkPreview(tweet: Tweet) {
        val link = extractLinkFrom(tweet.text)
        val textCrawler = TextCrawler()
        textCrawler.makePreview(this, link)
    }

}