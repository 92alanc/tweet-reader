package com.alancamargo.tweetreader.mock

import android.content.Context
import android.widget.Toast
import com.alancamargo.tweetreader.util.LinkClickListener
import com.alancamargo.tweetreader.util.LinkType

object MockLinkClickListener : LinkClickListener {

    override fun onLinkClicked(context: Context, link: String, linkType: LinkType) {
        Toast.makeText(context, link, Toast.LENGTH_SHORT).show()
    }

}