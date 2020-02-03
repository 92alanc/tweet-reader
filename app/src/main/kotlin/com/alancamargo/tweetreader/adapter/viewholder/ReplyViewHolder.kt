package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.ImageHandler
import com.alancamargo.tweetreader.util.LinkClickListener
import com.alancamargo.tweetreader.util.bindView

class ReplyViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : QuotedTweetViewHolder(itemView, imageHandler, linkClickListener) {

    private val progressBar by bindView<ProgressBar>(R.id.progress_bar)
    private val txtError by bindView<TextView>(R.id.txt_error)
    private val quotedTweet by bindView<FrameLayout>(R.id.quoted_tweet)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        quotedTweet.visibility = GONE
        progressBar.visibility = VISIBLE
        bindRepliedTweet(originalTweet.repliedTweet)
    }

    private fun bindRepliedTweet(repliedTweet: Tweet?) {
        progressBar.visibility = GONE
        quotedTweet.visibility = VISIBLE
        if (repliedTweet != null)
            super.bindQuotedTweet(repliedTweet)
        else
            showError()
    }

    private fun showError() {
        progressBar.visibility = GONE
        txtError.visibility = VISIBLE
    }

}