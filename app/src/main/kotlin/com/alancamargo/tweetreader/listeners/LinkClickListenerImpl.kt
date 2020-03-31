package com.alancamargo.tweetreader.listeners

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri

class LinkClickListenerImpl :
    LinkClickListener {

    override fun onLinkClicked(
        context: Context, link: String,
        linkType: LinkClickListener.LinkType
    ) {
        val url = when (linkType) {
            LinkClickListener.LinkType.MENTION -> {
                val userName = link.replace("@", "")
                "$BASE_URL/$userName"
            }

            LinkClickListener.LinkType.HASHTAG -> {
                val hashtag = link.replace("#", "%23")
                "$BASE_URL/search?q=$hashtag"
            }

            LinkClickListener.LinkType.PLAIN_URL -> link
        }

        val intent = Intent(ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    companion object {
        private const val BASE_URL = "http://twitter.com"
    }

}