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
import com.alancamargo.tweetreader.model.Link
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.LinkCallback
import com.alancamargo.tweetreader.repository.LinkRepository
import com.alancamargo.tweetreader.util.LinkType
import com.alancamargo.tweetreader.util.runAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LinkTweetViewHolder(itemView: View) : TweetViewHolder(itemView),
    LinkCallback,
    CoroutineScope {

    private val job = Job()

    override val coroutineContext = job + Dispatchers.Main

    private val repository = LinkRepository(itemView.context)

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

    override fun onStartLoading() {
        launch {
            previewCard.visibility = GONE
            progressBar.visibility = VISIBLE
        }
    }

    override fun onPreviewReady(link: Link) {
        runAsync {
            repository.saveLinkToCache(link)
        }

        launch {
            progressBar.visibility = GONE
            previewCard.visibility = VISIBLE

            link.thumbnail?.let {
                DependencyInjection.imageHandler.loadImage(it, imgThumbnail)
            }

            txtTitle.text = link.title
            txtLink.text = link.displayUrl
            txtContent.text = link.summary
            previewCard.setOnClickListener {
                DependencyInjection.linkClickListener.onLinkClicked(it.context, link.url,
                    LinkType.PLAIN_URL)
            }
        }
    }

    private fun loadLinkPreview(tweet: Tweet) {
        runAsync {
            repository.loadPreview(tweet, callback = this)
        }
    }

}