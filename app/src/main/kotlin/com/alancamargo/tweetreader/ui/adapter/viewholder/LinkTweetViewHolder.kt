package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.domain.tools.extractLink
import com.alancamargo.tweetreader.ui.entities.UiTweet
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import kotlinx.android.synthetic.main.item_tweet_link.*

class LinkTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener,
    shareButtonClickListener: ShareButtonClickListener?
) : TweetViewHolder(
    itemView,
    imageHandler,
    linkClickListener,
    shareButtonClickListener
) {

    override fun bindTo(tweet: UiTweet) {
        super.bindTo(tweet)

        tweet.fullText.extractLink()?.let { link ->
            with(txt_link) {
                text = link
                setOnClickListener {
                    linkClickListener.onLinkClicked(
                        context,
                        link,
                        LinkClickListener.LinkType.PLAIN_URL
                    )
                }
            }
        }
    }

}