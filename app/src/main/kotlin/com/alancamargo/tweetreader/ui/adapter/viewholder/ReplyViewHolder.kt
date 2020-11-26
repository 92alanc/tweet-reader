package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.framework.entities.Tweet
import kotlinx.android.synthetic.main.item_tweet_reply.*

class ReplyViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener,
    shareButtonClickListener: ShareButtonClickListener?
) : QuotedTweetViewHolder(
    itemView,
    imageHandler,
    linkClickListener,
    shareButtonClickListener
) {

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        quoted_tweet.visibility = GONE
        progress_bar.visibility = VISIBLE
        bindRepliedTweet(originalTweet.repliedTweet)
    }

    private fun bindRepliedTweet(repliedTweet: Tweet?) {
        progress_bar.visibility = GONE
        quoted_tweet.visibility = VISIBLE
        if (repliedTweet != null)
            super.bindQuotedTweet(repliedTweet)
        else
            showError()
    }

    private fun showError() {
        progress_bar.visibility = GONE
        txt_error.visibility = VISIBLE
    }

}