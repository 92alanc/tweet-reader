package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.ui.adapter.ViewPagerAdapter
import com.alancamargo.tweetreader.ui.tools.ImageHandler
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import kotlinx.android.synthetic.main.item_tweet_photo.*

class PhotoTweetViewHolder(
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

    override fun bindTo(tweet: TweetResponse) {
        super.bindTo(tweet)
        tweet.media?.getPhotoUrls()?.let { photos ->
            view_pager.adapter = ViewPagerAdapter(photos, imageHandler)
        }
    }

}