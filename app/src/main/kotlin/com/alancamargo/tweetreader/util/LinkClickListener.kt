package com.alancamargo.tweetreader.util

import android.content.Context

interface LinkClickListener {
    fun onLinkClicked(context: Context, link: String, linkType: LinkType)
}