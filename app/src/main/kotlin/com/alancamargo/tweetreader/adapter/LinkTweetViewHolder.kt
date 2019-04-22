package com.alancamargo.tweetreader.adapter

import android.view.View
import android.widget.TextView
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet
import com.alancamargo.tweetreader.util.extractLinkFrom

class LinkTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val txtLink = itemView.findViewById<TextView>(R.id.txt_link)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        val link = extractLinkFrom(tweet.text)
        txtLink.text = link
    }

}