package com.alancamargo.tweetreader.adapter

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.alancamargo.tweetreader.R
import com.alancamargo.tweetreader.model.Tweet

class PhotoTweetViewHolder(itemView: View) : TweetViewHolder(itemView) {

    private val viewPager = itemView.findViewById<ViewPager>(R.id.video_view)

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media?.contents?.map { it.photoUrl }?.let { photos ->
            viewPager.adapter = ViewPagerAdapter(photos)
        }
    }

}