package com.alancamargo.tweetreader.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.adapter.ViewPagerAdapter
import com.alancamargo.tweetreader.handlers.ImageHandler
import com.alancamargo.tweetreader.listeners.LinkClickListener
import com.alancamargo.tweetreader.model.Tweet
import kotlinx.android.synthetic.main.item_tweet_photo.*

class PhotoTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener
) : TweetViewHolder(itemView, imageHandler, linkClickListener) {

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media?.getPhotoUrls()?.let { photos ->
            view_pager.adapter = ViewPagerAdapter(photos, imageHandler)
        }
    }

}