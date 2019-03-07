package com.alancamargo.tweetreader.util

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri

class AppLinkClickListener : LinkClickListener {

    override fun onLinkClicked(context: Context, link: String, linkType: LinkType) {
        val url = when (linkType) {
            LinkType.MENTION -> {
                val userName = link.replace("@", "")
                "http://twitter.com/$userName"
            }

            LinkType.HASHTAG -> {
                val hashtag = link.replace("#", "%23")
                "http://twitter.com/search?q=$hashtag"
            }

            LinkType.PLAIN_URL -> link
        }

        val intent = Intent(ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

}