package com.alancamargo.tweetreader.ui.adapter.viewholder

import android.view.View
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.entities.User
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.ui.adapter.ViewPagerAdapter
import com.alancamargo.tweetreader.ui.entities.UiUser
import com.alancamargo.tweetreader.ui.listeners.LinkClickListener
import com.alancamargo.tweetreader.ui.listeners.ShareButtonClickListener
import com.alancamargo.tweetreader.ui.ads.ImageHandler
import kotlinx.android.synthetic.main.item_tweet_photo.*

class PhotoTweetViewHolder(
    itemView: View,
    imageHandler: ImageHandler,
    linkClickListener: LinkClickListener,
    shareButtonClickListener: ShareButtonClickListener?,
    userMapper: EntityMapper<User, UiUser>
) : TweetViewHolder(
    itemView,
    imageHandler,
    linkClickListener,
    shareButtonClickListener,
    userMapper
) {

    override fun bindTo(tweet: Tweet) {
        super.bindTo(tweet)
        tweet.media?.getPhotoUrls()?.let { photos ->
            view_pager.adapter = ViewPagerAdapter(photos, imageHandler)
        }
    }

}