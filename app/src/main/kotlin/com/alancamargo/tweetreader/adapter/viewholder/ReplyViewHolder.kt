package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.SingleTweetCallback
import com.alancamargo.tweetreader.repository.TweetRepository
import com.alancamargo.tweetreader.util.bindView

class ReplyViewHolder(itemView: View) : QuotedTweetViewHolder(itemView), SingleTweetCallback {

    private val progressBar by bindView<ProgressBar>(R.id.progress_bar)
    private val txtError by bindView<TextView>(R.id.txt_error)
    private val quotedTweet by bindView<FrameLayout>(R.id.quoted_tweet)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        val repository = TweetRepository(itemView.context)
        originalTweet.inReplyTo?.let { id ->
            repository.fetchSingleTweet(id, callback = this)
            quotedTweet.visibility = GONE
            progressBar.visibility = VISIBLE
        }
    }

    override fun onTweetLoaded(tweet: Tweet) {
        progressBar.visibility = GONE
        quotedTweet.visibility = VISIBLE
        originalTweet.quotedTweet = tweet
        super.bindQuotedTweet()
    }

    override fun onError() {
        progressBar.visibility = GONE
        txtError.visibility = VISIBLE
    }

}