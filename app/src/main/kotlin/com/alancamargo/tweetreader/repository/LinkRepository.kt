package com.alancamargo.tweetreader.repository

import android.content.Context
import com.alancamargo.tweetreader.database.TwitterDatabase
import com.alancamargo.tweetreader.model.Link
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extractLinkFrom
import com.leocardz.link.preview.library.LinkPreviewCallback
import com.leocardz.link.preview.library.SourceContent
import com.leocardz.link.preview.library.TextCrawler

class LinkRepository(context: Context) : LinkPreviewCallback {

    private val database = TwitterDatabase.getInstance(context).linkDao()

    private lateinit var tweet: Tweet
    private lateinit var callback: LinkCallback

    fun loadPreview(tweet: Tweet, callback: LinkCallback) {
        if (contains(tweet.id)) {
            callback.onPreviewReady(database.select(tweet.id))
        } else {
            this.tweet = tweet
            this.callback = callback
            val link = extractLinkFrom(tweet.text)
            val textCrawler = TextCrawler()
            textCrawler.makePreview(this, link)
        }
    }

    fun saveLinkToCache(link: Link) {
        if (!contains(link.tweetId))
            database.insert(link)
    }

    override fun onPre() {
        callback.onStartLoading()
    }

    override fun onPos(sourceContent: SourceContent, b: Boolean) {
        val title = sourceContent.title
        val url = sourceContent.url
        val displayUrl = sourceContent.cannonicalUrl
        val summary = sourceContent.description
        val thumbnail = if (sourceContent.images.isNotEmpty()) sourceContent.images.first() else null

        val link = Link(url, title, thumbnail, summary, displayUrl, tweet.id)
        callback.onPreviewReady(link)
    }

    private fun contains(tweetId: Long) = database.count(tweetId) > 0

}