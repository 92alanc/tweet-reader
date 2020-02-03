package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.helpers.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extractLinkFrom
import kotlinx.android.synthetic.main.item_tweet_link.*

class LinkTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)

        extractLinkFrom(tweet.text)?.let { link ->
            txt_link.run {
                text = link
                setOnClickListener {
                    linkClickListener.onLinkClicked(
                        context, link,
                        LinkClickListener.LinkType.PLAIN_URL
                    )
                }
            }
        }
    }

}