package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.repository.MyNewRepository
import com.alancamargo.tweetreader.util.bindView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReplyViewHolder(itemView: View) : QuotedTweetViewHolder(itemView) {

    private val progressBar by bindView<ProgressBar>(R.id.progress_bar)
    private val txtError by bindView<TextView>(R.id.txt_error)
    private val quotedTweet by bindView<FrameLayout>(R.id.quoted_tweet)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        quotedTweet.visibility = GONE
        progressBar.visibility = VISIBLE
        val repository = MyNewRepository(itemView.context)
        originalTweet.inReplyTo?.let { id ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // FIXME
                    val quotedTweet = repository.getTweet(id)
                    onTweetLoaded(quotedTweet)
                } catch (ex: Exception) {
                    onError()
                }
            }
        }
    }

    private fun onTweetLoaded(tweet: Tweet) {
        progressBar.visibility = GONE
        quotedTweet.visibility = VISIBLE
        originalTweet.quotedTweet = tweet
        super.bindQuotedTweet()
    }

    private fun onError() {
        progressBar.visibility = GONE
        txtError.visibility = VISIBLE
    }

}