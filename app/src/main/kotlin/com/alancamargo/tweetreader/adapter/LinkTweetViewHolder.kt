package com.alancamargo.tweetreader.adapter

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.di.DependencyInjection
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.LinkType
import com.alancamargo.tweetreader.util.extractLinkFrom

class LinkTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val txtLink = itemView.findViewById<TextView>(R.id.txt_link)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)

        extractLinkFrom(tweet.text)?.let { link ->
            txtLink.run {
                text = link
                setOnClickListener {
                    DependencyInjection.linkClickListener.onLinkClicked(
                        context, link, LinkType.PLAIN_URL
                    )
                }
            }
        }
    }

}