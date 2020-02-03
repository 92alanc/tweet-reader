package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.di.OldDependencyInjection
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.ImageHandler
import com.alancamargo.tweetreader.util.LinkType
import com.alancamargo.tweetreader.util.bindView
import com.alancamargo.tweetreader.util.extractLinkFrom

class LinkTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler
) : TweetViewHolder(itemView, imageHandler) {

    private val txtLink by bindView<TextView>(R.id.txt_link)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)

        extractLinkFrom(tweet.text)?.let { link ->
            txtLink.run {
                text = link
                setOnClickListener {
                    OldDependencyInjection.linkClickListener.onLinkClicked(
                        context, link, LinkType.PLAIN_URL
                    )
                }
            }
        }
    }

}