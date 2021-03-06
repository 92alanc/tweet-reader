package com.alancamargo.tweetreader.ui.listeners

import android.content.Context

interface LinkClickListener {
    fun onLinkClicked(context: Context, link: String, linkType: LinkType)

    enum class LinkType {
        HASHTAG,
        MENTION,
        PLAIN_URL
    }
}