package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.*

class LinkTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    private val txtLink by bindView<TextView>(R.id.txt_link)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)

        extractLinkFrom(tweet.text)?.let { link ->
            txtLink.run {
                text = link
                setOnClickListener {
                    linkClickListener.onLinkClicked(context, link, LinkType.PLAIN_URL)
                }
            }
        }
    }

}